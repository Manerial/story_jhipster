package com.jher.nid_aux_histoires.service.tool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.jher.nid_aux_histoires.domain.WordAnalysis;

public class WordGenerator {
	private static Random random = new Random();
	private JSONObject wordAnalysis;

	public WordGenerator(WordAnalysis wordAnalysis) throws JSONException {
		this.wordAnalysis = new JSONObject(wordAnalysis.getAnalysis());
	}

	/**
	 * Create a certain amount of words with a fixed length using the analysis of a
	 * list of words
	 * 
	 * @param numberOfWords : Number of word to generate
	 * @param wordLength    : The length of the generated words. If <= 0, random
	 * @throws JSONException
	 */
	public List<String> generateWords(int numberOfWords, int fixLength) throws JSONException {
		List<String> generatedWords = new ArrayList<>();

		int unlockInfinite = 0;
		while (generatedWords.size() < numberOfWords && unlockInfinite < 100) {
			String newWord = generateWord();
			unlockInfinite++;
			if (checkWordLength(fixLength, newWord) && !generatedWords.contains(newWord)) {
				generatedWords.add(newWord);
				unlockInfinite = 0;
			}
		}

		return generatedWords;
	}

	private boolean checkWordLength(int fixLength, String word) {
		return fixLength <= 1 || word.length() == fixLength;
	}

	/**
	 * Create a new word using the parameters of the analyzer
	 * 
	 * @return a new word
	 * @throws JSONException
	 */
	public String generateWord() throws JSONException {
		String newWord = getRandomWordBeginning();
		if (newWord.length() == 1) {
			return newWord;
		}

		while (true) {
			String nextChar = getNextChar(newWord);
			newWord += nextChar;

			if (endOfWord(nextChar)) {
				break;
			}
		}
		return newWord;
	}

	private String getNextChar(String newWord) throws JSONException {
		String nextChar = "";
		JSONObject nextCharPossibilities = getNextCharPossibilities(newWord);
		int sumOfPreviousCharRank = 0;
		int randomNextCharRank = getRandomCharRank(nextCharPossibilities);
		Iterator<?> iteratorNextChar = nextCharPossibilities.keys();
		while (!rankFound(sumOfPreviousCharRank, randomNextCharRank) && iteratorNextChar.hasNext()) {
			nextChar = iteratorNextChar.next().toString();
			sumOfPreviousCharRank += nextCharPossibilities.getInt(nextChar);
		}
		return nextChar;
	}

	private JSONObject getNextCharPossibilities(String word) throws JSONException {
		String lastBigram = (word.length() >= 2) ? word.substring(word.length() - 2, word.length()) : word;
		JSONObject nextTrigrams = wordAnalysis.getJSONObject(lastBigram);
		return nextTrigrams;
	}

	/**
	 * Get a random character rank in all the availables trigrams
	 * 
	 * @param possibilities : all the availables trigrams
	 * @return the random rank of a character
	 * @throws JSONException : All the JSON exceptions
	 */
	private int getRandomCharRank(JSONObject possibilities) throws JSONException {
		int sumOfTrigramsFrequency = getSumOfPossibilitiesFrequency(possibilities);
		return random.nextInt(sumOfTrigramsFrequency) + 1;
	}

	/**
	 * Check if the character is a word end (empty)
	 * 
	 * @param character : the character to check
	 * @return true if the character is a word end
	 */
	private boolean endOfWord(String character) {
		return character.equals("");
	}

	/**
	 * Get the two first letters of a word
	 * 
	 * @return The two first letters of a word
	 * @throws JSONException : All the JSON exceptions
	 */
	private String getRandomWordBeginning() throws JSONException {
		JSONObject wordBeginnings = getWordBeginnings();

		// Get first letter
		int rankToFind = random.nextInt(getSumOfAllTrigramsFrequencies(wordBeginnings)) + 1;
		String beginning = "";

		int sumOfPreviousTrigramsFrequency = 0;
		Iterator<?> beginningsKeys = wordBeginnings.keys();
		while (!rankFound(sumOfPreviousTrigramsFrequency, rankToFind) && beginningsKeys.hasNext()) {
			beginning = beginningsKeys.next().toString();
			JSONObject beginningTrigram = wordBeginnings.getJSONObject(beginning);
			sumOfPreviousTrigramsFrequency += getSumOfTrigramsFrequency(beginningTrigram);
		}

		return beginning + getNextChar(beginning);
	}

	private int getSumOfAllTrigramsFrequencies(JSONObject trigrams) throws JSONException {
		int sumFrequencies = 0;
		Iterator<?> trigramKeys = trigrams.keys();
		while (trigramKeys.hasNext()) {
			String trigramKey = trigramKeys.next().toString();
			sumFrequencies += getSumOfTrigramsFrequency(trigrams.getJSONObject(trigramKey));
		}
		return sumFrequencies;
	}

	private int getSumOfTrigramsFrequency(JSONObject trigram) throws JSONException {
		int sumFrequencies = 0;
		Iterator<?> thirdLetters = trigram.keys();
		while (thirdLetters.hasNext()) {
			String thirdLetter = thirdLetters.next().toString();
			sumFrequencies += trigram.getInt(thirdLetter);
		}
		return sumFrequencies;
	}

	private JSONObject getWordBeginnings() throws JSONException {
		JSONObject wordBeginnings = new JSONObject();
		Iterator<?> iterator = wordAnalysis.keys();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			if (key.length() == 1) {
				wordBeginnings.put(key, wordAnalysis.getJSONObject(key));
			}
		}
		return wordBeginnings;
	}

	/**
	 * Get the number of appearance of a bigram
	 * 
	 * @param possibilities : The bigram we need to get the frequency
	 * @return The frequency of appearance of a bigram
	 * @throws JSONException : All the JSON exceptions
	 */
	private int getSumOfPossibilitiesFrequency(JSONObject possibilities) throws JSONException {
		int sumFrequencies = 0;
		Iterator<?> nextPossibilities = possibilities.keys();
		while (nextPossibilities.hasNext()) {
			String nextLetter = nextPossibilities.next().toString();
			sumFrequencies += possibilities.getInt(nextLetter);
		}
		return sumFrequencies;
	}

	/**
	 * Check if the current rank matches the searched rank
	 * 
	 * @param currentRank  : The current rank
	 * @param searchedRank : The rank we look for
	 * @return true if the current rank is greater than the searched rank
	 */
	private boolean rankFound(int currentRank, int searchedRank) {
		return currentRank >= searchedRank;
	}
}