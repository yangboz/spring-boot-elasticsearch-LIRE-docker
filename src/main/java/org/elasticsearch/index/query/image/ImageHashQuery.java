package org.elasticsearch.index.query.image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.util.ToStringUtils;
import net.semanticmetadata.lire.imageanalysis.features.LireFeature;

import static org.apache.lucene.search.DocIdSetIterator.NO_MORE_DOCS;

/**
 * Copied from {@link TermQuery}, query by hash first and only calculate score for matching docs
 */
public class ImageHashQuery extends Query {
    private final Term term;

    private String luceneFieldName;
    private LireFeature lireFeature;
    private ImageScoreCache imageScoreCache;
    
    public ImageHashQuery(Term t, String luceneFieldName, LireFeature lireFeature, ImageScoreCache imageScoreCache, float boost) {
        this.term = t;
        this.luceneFieldName = luceneFieldName;
        this.lireFeature = lireFeature;
        this.imageScoreCache = imageScoreCache;
        setBoost(boost);
    }

    @Override
    public Weight createWeight(IndexSearcher searcher, boolean needsScores) throws IOException {
	
        final IndexReaderContext context = searcher.getTopReaderContext();
        final TermContext termState = TermContext.build(context, term);
        
        return new ImageHashWeight(searcher, needsScores, termState);
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ImageHashQuery))
            return false;
        ImageHashQuery other = (ImageHashQuery)o;
        return (this.getBoost() == other.getBoost())
                && this.term.equals(other.term)
                & luceneFieldName.equals(luceneFieldName)
                && lireFeature.equals(lireFeature);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + term.hashCode();
        result = 31 * result + luceneFieldName.hashCode();
        result = 31 * result + lireFeature.hashCode();
        result = Float.floatToIntBits(getBoost()) ^ result;
        return result;
    }
    
    @Override
    public String toString(String field) {
        StringBuilder buffer = new StringBuilder();
        if (!term.field().equals(field)) {
            buffer.append(term.field());
            buffer.append(":");
        }
        buffer.append(term.text());
        buffer.append(";");
        buffer.append(luceneFieldName);
        buffer.append(",");
        buffer.append(lireFeature.getClass().getSimpleName());
        buffer.append(ToStringUtils.boost(getBoost()));
        return buffer.toString();
    }
    
    
    final class ImageHashWeight extends Weight 
    {
        private final TermContext termStates;

        public ImageHashWeight(IndexSearcher searcher, boolean needsScores, TermContext termStates) throws IOException {
            super(ImageHashQuery.this);
            assert termStates != null : "TermContext must not be null";
            this.termStates = termStates;
        }

        @Override
        public String toString() { return "weight(" + ImageHashQuery.this + ")"; }

        @Override
        public float getValueForNormalization() {
            return 1f;
        }

        @Override
        public void normalize(float queryNorm, float topLevelBoost) {
        }

        @Override
        public Scorer scorer(LeafReaderContext context) throws IOException {
            assert termStates.topReaderContext == ReaderUtil.getTopLevelContext(context) : "The top-reader used to create Weight (" + termStates.topReaderContext + ") is not the same as the current reader's top-reader (" + ReaderUtil.getTopLevelContext(context);
            final TermsEnum termsEnum = getTermsEnum(context);
            if (termsEnum == null) {
                return null;
            }
            PostingsEnum docs = termsEnum.postings( null);
            assert docs != null;
            return new ImageHashScorer(this, docs, context.reader());
        }

        private TermsEnum getTermsEnum(LeafReaderContext context) throws IOException {
            final TermState state = termStates.get(context.ord);
            if (state == null) { // term is not present in that reader
                assert termNotInReader(context.reader(), term) : "no termstate found but term exists in reader term=" + term;
                return null;
            }
            final TermsEnum termsEnum = context.reader().terms(term.field()).iterator();
            termsEnum.seekExact(term.bytes(), state);
            return termsEnum;
        }

        private boolean termNotInReader(LeafReader reader, Term term) throws IOException {
            return reader.docFreq(term) == 0;
        }

        @Override
        public Explanation explain(LeafReaderContext context, int doc) throws IOException {
            Scorer scorer = scorer(context);
            boolean exists = (scorer != null && scorer.iterator().advance(doc) == doc);
            //scorer.

            if(exists){
                float score = scorer.score();
                List<Explanation> details=new ArrayList<>();
                if (getBoost() != 1.0f) {
                    details.add(Explanation.match(getBoost(), "boost"));
                    score = score / getBoost();
                }
                details.add(Explanation.match(score ,"image score (1/distance)"));
                return Explanation.match(
                         score, ImageHashQuery.this.toString() + ", product of:",details);

            }else{
                return Explanation.noMatch(ImageHashQuery.this.toString() + " doesn't match id " + doc);
            }
        }

        @Override
        public void extractTerms(Set<Term> terms) {

        }
    }
    
    
    final class ImageHashScorer extends AbstractImageScorer 
    {
        private final PostingsEnum docsEnum;
        private final IndexReader reader;

        ImageHashScorer(Weight weight, PostingsEnum td, IndexReader reader) {
            super(weight, luceneFieldName, lireFeature, reader, ImageHashQuery.this.getBoost());
            this.docsEnum = td;
            this.reader = reader;
        }

        @Override
        public int docID() {
            return docsEnum.docID();
        }

        @Override
        public float score() throws IOException {
            assert docID() != NO_MORE_DOCS;
            int docId = docID();
            String cacheKey = reader.toString() + ":" + docId;
            if (imageScoreCache.getScore(cacheKey) != null) {
                return 0f;  // BooleanScorer will add all score together, return 0 for docs already processed
            }
            float score = super.score();
            imageScoreCache.setScore(cacheKey, score);
            return score;
        }

        @Override
        public DocIdSetIterator iterator() {
            // added for lucene 5.5.0
            return new DocIdSetIterator() {

                @Override
                public int docID() {
                    return docsEnum.docID();
                }

                @Override
                public int nextDoc() throws IOException {
                    return docsEnum.nextDoc();
                }

                @Override
                public int advance(int target) throws IOException {
                    return docsEnum.advance(target);
                }

                @Override
                public long cost() {
                    return docsEnum.cost();
                }
            };
        }
    }
    

    
}
