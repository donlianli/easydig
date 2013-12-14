package com.donlian.lucene;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;
import org.apache.lucene.util.Version;

public class PorterStemAnalyzer extends Analyzer {
	@Override
	protected TokenStreamComponents createComponents(String fieldName,
			Reader reader) {
		return new TokenStreamComponents(new LowerCaseTokenizer(Version.LUCENE_43, reader));
	}
}