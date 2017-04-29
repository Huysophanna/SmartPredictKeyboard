package com.arcanetecher.smartpredictkeyboard;

import android.renderscript.ScriptGroup;
import android.service.textservice.SpellCheckerService;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.inputmethod.InputConnection;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by huysophanna on 4/26/17.
 */

public class MySpellingSession extends SpellCheckerService.Session {
    private String suggestions[] = null;
    private HashMap<String, String[]> dictionary = new HashMap<>();
    private final String VOWEL = "-vowel";
    private final String CONSONANT = "-consonant";

    @Override
    public void onCreate() {

        //method to initialize all data
        InitializeDictionaryData();

    }

//    public String[][] AddVowelConsonantKey(String val) {
//        VOWEL = new String[][]{{"vowel", val}};
//        return VOWEL;
//    }


    private void InitializeDictionaryData() {
//        Log.d("DICTIONARY KEY/VALUE PAIR:", String.valueOf(dictionary.get("ban").toString()));

        //VOWEL PART INITIALIZATION
        dictionary.put("a" + VOWEL, new String[]{"ា", "ាំ", "អា"});
        dictionary.put("e" + VOWEL , new String[]{"េ", "ែ", "អ៉ី"});
        dictionary.put("i" + VOWEL, new String[]{"ិ", "ី", "អាយ"});


    }

    @Override
    public SuggestionsInfo onGetSuggestions(TextInfo textInfo, int suggestionsLimit) {
        String word = textInfo.getText();
        /*
        * DICTIONARY LOGIC DECLARATION
        * */
        String[] SplitArrayTemp = new String[]{};
        String VowelConsonantTag = "";
        String InputCharacter = "";
        String DictionaryKey = "";

        //retrieve all dictionary data with key & value

        if(dictionary != null && !dictionary.isEmpty()){
            for(String key : dictionary.keySet()){

//                Split into 2 strings
                    SplitArrayTemp = key.split("-");
                    InputCharacter = SplitArrayTemp[0];
                    VowelConsonantTag = SplitArrayTemp[1];

                    if (word.equalsIgnoreCase(InputCharacter)) {
                        DictionaryKey = key;
                        break;
                    }
            }
        }

        Log.d("okok", InputCharacter);
        Log.d("okok", VowelConsonantTag);


        //set suggestion dictionary data by getting from our dictionary HashMap key
        suggestions = dictionary.get(DictionaryKey);


//        switch (word.toLowerCase()) {
//
//            //VOWEL PART
//            case "a":
//
//
//                suggestions = dictionary.get(DictionaryKey);
////                suggestions = new String[]{"ា", "ាំ", "អា"};
////                Log.d("", suggestions.toString());
//                break;
//            case "e":
//                suggestions = new String[]{"េ", "ែ", "អ៉ី"};
//                break;
//            case "i":
//                suggestions = new String[]{"ិ", "ី", "អាយ"};
//                break;
//            case "o":
//                suggestions = new String[]{"ោ", "ៅ", "ូ"};
//                break;
//            case "u":
//                suggestions = new String[]{"ុ", "ូ", "យូ"};
//                break;
//
//            //CONSONANT PART
//            case "b":
//                suggestions = new String[]{"ប", "ព", "្ប", "្ព"};
//                break;
//            case "h":
//                suggestions = new String[]{"ហ", "ះ", "្ហ"};
//                break;
//            case "n":
//                suggestions = new String[]{"ន", "ណ", "្ន", "្ណ"};
//                break;
//
//            case "nh":
//                suggestions = new String[]{"ញ", "ខ្ញុំ", "ញុំ"};
//                break;
//            case "nhom":
//                suggestions = new String[]{"ខ្ញុំ", "ញុំ"};
//                break;
//            case "jong":
//                suggestions = new String[]{"ចង់", "ចង", "ជង់"};
//                break;
//            default:
//                suggestions = new String[]{};
//                break;
//        }

//        if(word.equals("")){
//            suggestions = new String[]{"Pedro", "ផេតត្រូ", "Petar", "Pierre", "Petrus"};
//        }else {
//            suggestions = new String[]{};
//        }

        SuggestionsInfo suggestionsInfo = new
                SuggestionsInfo(SuggestionsInfo.RESULT_ATTR_LOOKS_LIKE_TYPO, suggestions);

//        Log.d("WORD:", word);
//
//        Log.d("SUGGESTION:", suggestions.toString());
//        Log.d("SUGGESTION-INFO:", suggestionsInfo.toString());
        return suggestionsInfo;
    }

    @Override
    public SentenceSuggestionsInfo[] onGetSentenceSuggestionsMultiple(TextInfo[] textInfos, int suggestionsLimit) {
        List<SuggestionsInfo> suggestionsInfos = new ArrayList<>();

        for(int i=0; i<textInfos.length; i++){
            TextInfo cur = textInfos[i];

            // Convert the sentence into an array of words
            String words[] = cur.getText().split("\\s+");
            for(String word:words){
                TextInfo tmp = new TextInfo(word);
                // Generate suggestions for each word
                suggestionsInfos.add(onGetSuggestions(tmp, suggestionsLimit));
            }
        }
        return new SentenceSuggestionsInfo[]{
                new SentenceSuggestionsInfo(
                        suggestionsInfos.toArray(new SuggestionsInfo[suggestionsInfos.size()]),
                        new int[suggestionsInfos.size()],
                        new int[suggestionsInfos.size()]
                )
        };
    }
}
