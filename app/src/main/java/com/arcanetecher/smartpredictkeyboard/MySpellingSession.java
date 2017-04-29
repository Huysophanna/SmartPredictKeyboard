package com.arcanetecher.smartpredictkeyboard;

import android.renderscript.ScriptGroup;
import android.content.Context;
import android.content.res.Resources;
import android.service.textservice.SpellCheckerService;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.inputmethod.InputConnection;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;

import java.io.InputStream;
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
    private final String SPECIALVOWEL = "-specialvowel";
    private final String NUMBER = "-number";

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
        dictionary.put("i" + VOWEL, new String[]{"ិ", "ី", "អាយ"});
        dictionary.put("w" + VOWEL, new String[]{"ឹ", "ឺ"});
        dictionary.put("u" + VOWEL, new String[]{"ុ", "ូ", "យូ"});
        dictionary.put("[" + VOWEL, new String[]{"ៀ", "ឿ"});
        dictionary.put("e" + VOWEL , new String[]{"េ", "ែ", "អ៉ី", "ឯ"});
        dictionary.put("o" + VOWEL, new String[]{"ោ", "ៅ", "ូ"});
        dictionary.put("," + VOWEL, new String[]{"ុំ", "ុះ"});
        dictionary.put(";" + VOWEL, new String[]{"ើ", "ោះ"});


        //CONSONANT PART INITIALIZATION
        dictionary.put("k" + CONSONANT, new String[]{"ក", "គ", "្ក", "្គ"});
        dictionary.put("x" + CONSONANT, new String[]{"ខ", "ឃ", "្ខ", "្ឃ"});
        dictionary.put("g" + CONSONANT, new String[]{"ង", "អ", "្ង", "្អ"});
        dictionary.put("c" + CONSONANT, new String[]{"ច", "ជ", "្ច", "្ជ"});
        dictionary.put("q" + CONSONANT, new String[]{"ឆ", "ឈ", "្ឆ", "្ឈ"});
        dictionary.put("j" + CONSONANT, new String[]{"ញ", "្ញ"});
        dictionary.put("d" + CONSONANT, new String[]{"ដ", "ឌ", "្ដ", "្ឌ"});
        dictionary.put("f" + CONSONANT, new String[]{"ថ", "ធ", "្ថ", "្ធ"});
        dictionary.put("t" + CONSONANT, new String[]{"ត", "ទ", "្ត", "្ទ"});
        dictionary.put("n" + CONSONANT, new String[]{"ន", "ណ", "្ន", "្ណ"});
        dictionary.put("z" + CONSONANT, new String[]{"ឋ", "ឍ", "្ឋ", "្ឍ"});
        dictionary.put("b" + CONSONANT, new String[]{"ប", "ព", "្ប", "្ព"});
        dictionary.put("p" + CONSONANT, new String[]{"ផ", "ភ", "្ផ", "្ភ"});
        dictionary.put("m" + CONSONANT, new String[]{"ម", "្ម", "ំ"});
        dictionary.put("y" + CONSONANT, new String[]{"យ", "្យ", "ួ"});
        dictionary.put("r" + CONSONANT, new String[]{"រ", "្រ", "ឬ"});
        dictionary.put("l" + CONSONANT, new String[]{"ល", "ឡ", "្ល" , "្ឡ"});
        dictionary.put("v" + CONSONANT, new String[]{"វ", "្វ", "េះ", "ៈ"});
        dictionary.put("s" + CONSONANT, new String[]{"ស", "្ស", "ៃ"});
        dictionary.put("h" + CONSONANT, new String[]{"ហ", "ះ", "្ហ"});

        //SPECIAL VOWEL & NUMBER PART INITIALIZATION
        dictionary.put("'" + SPECIALVOWEL, new String[]{"់", "៉"});
        dictionary.put("bontok" + SPECIALVOWEL, new String[]{"់", "៉"});
        dictionary.put("\"" + SPECIALVOWEL, new String[]{"់", "៉"});
        dictionary.put("treysab" + SPECIALVOWEL, new String[]{"៊"});
        dictionary.put("ao" + SPECIALVOWEL, new String[]{"ឪ", "ឧ"});
        dictionary.put("]" + SPECIALVOWEL, new String[]{"ឪ", "ឧ"});
        dictionary.put("1" + NUMBER, new String[]{"១", "មួយ"});
        dictionary.put("2" + NUMBER, new String[]{"២", "ពីរ", "ៗ"});
        dictionary.put("3" + NUMBER, new String[]{"៣", "បី"});
        dictionary.put("4" + NUMBER, new String[]{"៤", "បួន", "៛"});
        dictionary.put("5" + NUMBER, new String[]{"៥", "ប្រាំ", "%"});
        dictionary.put("6" + NUMBER, new String[]{"៦", "ប្រាំមួយ", "៍"});
        dictionary.put("7" + NUMBER, new String[]{"៧", "ប្រាំពីរ", "័"});
        dictionary.put("8" + NUMBER, new String[]{"៨", "ប្រាំបី", "៏"});
        dictionary.put("9" + NUMBER, new String[]{"៩", "ប្រាំបួន"});
        dictionary.put("ey" + NUMBER, new String[]{"អី", "ឥ", "៌"});
        dictionary.put("oy" + SPECIALVOWEL, new String[]{"ឲ្យ", "អោយ", "៎"});
        dictionary.put("\\" + SPECIALVOWEL, new String[]{"ឭ", "ឮ"});

        //SPECIAL FAMILAIR COMBINATION
        dictionary.put("nh" + CONSONANT, new String[]{"ញ", "្ញ", "ខ្ញុំ", "ញុំ"});
        dictionary.put("th" + CONSONANT, new String[]{"ថ", "ធ", "ឋ", "ឍ", "្ថ", "្ធ", "្ឋ", "្ឍ"});
        dictionary.put("chh" + CONSONANT, new String[]{"ឆ", "ឈ", "្ឆ", "្ឈ"});
        dictionary.put("ch" + CONSONANT, new String[]{"ឆ", "ឈ", "ច", "ជ", "្ឆ", "្ឈ", "្ច", "្ជ"});
        dictionary.put("ph" + CONSONANT, new String[]{"ផ", "ភ", "្ផ", "្ភ"});
        dictionary.put("khor" + CONSONANT, new String[]{"ខ", "ឃ", "្ខ", "្ឃ"});
        dictionary.put("tng" + CONSONANT, new String[]{"ថ្ង", "ធ្ង", "ថ្ងៃ", "ធ្ងន់"});

        //WORD DEFINED PART INITIALIZATION
        dictionary.put("bay" + CONSONANT, new String[]{"បាយ"});
        dictionary.put("bat" + CONSONANT, new String[]{"បាត់", "បាត", "បាទ"});
        dictionary.put("bart" + CONSONANT, new String[]{"បាទ", "បាត"});
        dictionary.put("da" + CONSONANT, new String[]{"ដា", "ដារ"});
        dictionary.put("del" + CONSONANT, new String[]{"ដែល", "ដែរ", "ឌែល"});
        dictionary.put("dae" + CONSONANT, new String[]{"ដែល", "ដែរ", "ឌែល"});
        dictionary.put("dor" + CONSONANT, new String[]{"ដ៏", "ដ", "ដរ"});
        dictionary.put("jong" + CONSONANT, new String[]{"ចង់", "ចង", "ជង់"});
        dictionary.put("ja" + CONSONANT, new String[]{"ចា៎", "ចារ", "ចាស់"});
        dictionary.put("jas" + CONSONANT, new String[]{"ចាស់", "ចា៎", "ចារ"});
        dictionary.put("ka" + CONSONANT, new String[]{"ការ", "កា", "ការណ៍"});
        dictionary.put("kernh" + CONSONANT, new String[]{"ឃេីញ"});
        dictionary.put("knhom" + CONSONANT, new String[]{"ខ្ញុំ", "ញុំ"});
        dictionary.put("la" + CONSONANT, new String[]{"ឡា", "លា"});
        dictionary.put("min" + CONSONANT, new String[]{"មិន", "មីន"});
        dictionary.put("men" + CONSONANT, new String[]{"មិន", "មីន"});
        dictionary.put("nhom" + CONSONANT, new String[]{"ខ្ញុំ", "ញុំ"});
        dictionary.put("nov" + CONSONANT, new String[]{"នៅ", "នូវ"});
        dictionary.put("na" + CONSONANT, new String[]{"ណា", "ណា៎"});
        dictionary.put("nas" + CONSONANT, new String[]{"ណាស់"});
        dictionary.put("or" + CONSONANT, new String[]{"អ", "អរ"});
        dictionary.put("pong" + CONSONANT, new String[]{"ផង", "ផង់", "ភង់"});
        dictionary.put("ptes" + CONSONANT, new String[]{"ផ្ទះ"});
        dictionary.put("riel" + CONSONANT, new String[]{"៛", "រៀល"});
        dictionary.put("rean" + CONSONANT, new String[]{"រៀន"});
        dictionary.put("ra" + CONSONANT, new String[]{"រា", "រ៉ា"});
        dictionary.put("sa" + CONSONANT, new String[]{"សា", "សារ"});
        dictionary.put("sala" + CONSONANT, new String[]{"សាលា", "សាលារៀន"});
        dictionary.put("seksa" + CONSONANT, new String[]{"សិក្សា"});
        dictionary.put("sabay" + CONSONANT, new String[]{"សប្បាយ"});
        dictionary.put("saart" + CONSONANT, new String[]{"ស្អាត", "ស្អាតបាត"});
        dictionary.put("sos" + CONSONANT, new String[]{"សោះ", "សស់"});
        dictionary.put("sros" + CONSONANT, new String[]{"ស្រស់", "ស្រស់ស្អាត"});
        dictionary.put("tov" + CONSONANT, new String[]{"ទៅ"});
        dictionary.put("tes" + CONSONANT, new String[]{"ទះ", "ផ្ទះ"});
        dictionary.put("tam" + CONSONANT, new String[]{"តាម", "ថាម"});
        dictionary.put("team" + CONSONANT, new String[]{"ទាម", "ទាមទារ"});
        dictionary.put("tea" + CONSONANT, new String[]{"ទា", "ទារ", "តៀរ"});
        dictionary.put("tngai" + CONSONANT, new String[]{"ថ្ងៃ"});
        dictionary.put("yor" + CONSONANT, new String[]{"យក"});
        dictionary.put("yok" + CONSONANT, new String[]{"យក"});









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
