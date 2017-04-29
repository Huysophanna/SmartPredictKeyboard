package com.arcanetecher.smartpredictkeyboard;

import android.content.Context;
import android.content.res.Resources;
import android.service.textservice.SpellCheckerService;
import android.util.Log;
import android.view.inputmethod.InputConnection;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huysophanna on 4/26/17.
 */

public class MySpellingSession extends SpellCheckerService.Session {
    @Override
    public void onCreate() {
    }

    @Override
    public SuggestionsInfo onGetSuggestions(TextInfo textInfo, int suggestionsLimit) {
        String word = textInfo.getText();
        String suggestions[] = null;

        /*
        * DICTIONARY LOGIC DECLARATION
        * */
        switch (word.toLowerCase()) {
            case "n":
                suggestions = new String[]{"ន", "ណ", "្ន", "្ណ"};
                break;
            case "nhom":
                suggestions = new String[]{"ខ្ញុំ", "ញុំ"};
                break;
            case "jong":
                suggestions = new String[]{"ចង", "ជង់"};
                break;
            default:
                suggestions = new String[]{};
                break;
        }

//        if(word.equals("")){
//            suggestions = new String[]{"Pedro", "ផេតត្រូ", "Petar", "Pierre", "Petrus"};
//        }else {
//            suggestions = new String[]{};
//        }

        SuggestionsInfo suggestionsInfo = new
                SuggestionsInfo(SuggestionsInfo.RESULT_ATTR_LOOKS_LIKE_TYPO, suggestions);

        Log.d("WORD:", word);

        Log.d("SUGGESTION:", suggestions.toString());
        Log.d("SUGGESTION-INFO:", suggestionsInfo.toString());
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
