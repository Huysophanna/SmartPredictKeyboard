package com.arcanetecher.smartpredictkeyboard;

import android.content.ContentResolver;
import android.renderscript.ScriptGroup;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.provider.UserDictionary;
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
        dictionary.put("a" + VOWEL, new String[]{"ា", "ាំ", "អា", "អា៎"});
        dictionary.put("i" + VOWEL, new String[]{"ិ", "ី", "អាយ"});
        dictionary.put("w" + VOWEL, new String[]{"ឹ", "ឺ"});
        dictionary.put("u" + VOWEL, new String[]{"ុ", "ូ", "យូ"});
        dictionary.put("[" + VOWEL, new String[]{"ៀ", "ឿ"});
        dictionary.put("e" + VOWEL , new String[]{"េ", "ែ", "អ៉ី", "ឯ"});
        dictionary.put("o" + VOWEL, new String[]{"អូន", "ោ", "ៅ", "ូ"});
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
        dictionary.put("b" + CONSONANT, new String[]{"បង", "ប", "ព", "្ប", "្ព"});
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
        dictionary.put("0" + NUMBER, new String[]{"០", "សូន្យ"});
        dictionary.put("ey" + NUMBER, new String[]{"អី", "ឥ", "៌"});
        dictionary.put("ei" + NUMBER, new String[]{"អី", "ឥ", "៌"});
        dictionary.put("oy" + SPECIALVOWEL, new String[]{"ឲ្យ", "អោយ", "៎"});
        dictionary.put("\\" + SPECIALVOWEL, new String[]{"ឭ", "ឮ"});

        //SPECIAL FAMILAIR COMBINATION
        dictionary.put("ah" + VOWEL, new String[]{"ា", "ាំ", "អា", "អារ", "អា៎"});
        dictionary.put("br" + CONSONANT, new String[]{"ប្រ", "ព្រ", "ប្រូ"});
        dictionary.put("ng" + CONSONANT, new String[]{"ង", "ងរ", "ងងឹត", "ងងឹត", "ងាយ"});
        dictionary.put("chh" + CONSONANT, new String[]{"ឆ", "ឈ", "្ឆ", "្ឈ"});
        dictionary.put("ch" + CONSONANT, new String[]{"ឆ", "ឈ", "ច", "ជ", "្ឆ", "្ឈ", "្ច", "្ជ"});
        dictionary.put("jb" + CONSONANT, new String[]{"ច្ប", "ឆ្ប", "ច្បាស់", "ច្បាស់លាស់"});
        dictionary.put("chb" + CONSONANT, new String[]{"ច្ប", "ឆ្ប", "ច្បាស់", "ច្បាស់លាស់"});
        dictionary.put("khor" + CONSONANT, new String[]{"ខ", "ឃ", "្ខ", "្ឃ"});
        dictionary.put("kr" + CONSONANT, new String[]{"ក្រ", "គ្រ", "ក្រមា", "គ្រែ"});
        dictionary.put("kn" + CONSONANT, new String[]{"គ្ន", "ក្ន", "ក្នុង", "គ្នា", "គ្នីគ្នា"});
        dictionary.put("nh" + CONSONANT, new String[]{"ញ", "្ញ", "ខ្ញុំ", "ញុំ"});
        dictionary.put("mj" + CONSONANT, new String[]{"ម្ច", "ម្ជ", "ម្ចាស់", "ម្ជូរ"});
        dictionary.put("mn" + CONSONANT, new String[]{"ម្ន", "ម្នាក់", "ម្នាស់", "ម្នីម្នា"});
        dictionary.put("ph" + CONSONANT, new String[]{"ផ", "ភ", "្ផ", "្ភ"});
        dictionary.put("pr" + CONSONANT, new String[]{"ប្រ", "ព្រ", "ប្រូ"});
        dictionary.put("pl" + CONSONANT, new String[]{"ផ្ល", "ភ្ល", "ផ្លែ", "ភ្លក់"});
        dictionary.put("phl" + CONSONANT, new String[]{"ផ្ល", "ភ្ល", "ផ្លែ", "ភ្លក់"});
        dictionary.put("tng" + CONSONANT, new String[]{"ថ្ង", "ធ្ង", "ថ្ងៃ", "ធ្ងន់"});
        dictionary.put("tl" + CONSONANT, new String[]{"ថ្ល", "ធ្ល", "ថ្លៃ", "ធ្លាយ"});
        dictionary.put("thl" + CONSONANT, new String[]{"ថ្ល", "ធ្ល", "ថ្លៃ", "ធ្លាយ"});
        dictionary.put("th" + CONSONANT, new String[]{"ថ", "ធ", "ឋ", "ឍ", "្ថ", "្ធ", "្ឋ", "្ឍ"});
        dictionary.put("tm" + CONSONANT, new String[]{"ថ្ម", "ធ្ម", "ថ្មី", "ធ្មេញ"});
        dictionary.put("thm" + CONSONANT, new String[]{"ថ្ម", "ធ្ម", "ថ្មី", "ធ្មេញ"});
        dictionary.put("sr" + CONSONANT, new String[]{"ស្រ", "ស្រី", "ស្រលាញ់", "ស្រា"});
        dictionary.put("sro" + CONSONANT, new String[]{"ស្រ", "ស្រី", "ស្រលាញ់", "ស្រលះ"});


        //WORD DEFINED PART INITIALIZATION
        dictionary.put("ai" + CONSONANT, new String[]{"ហើយ", "អាយ"});
        dictionary.put("alov" + CONSONANT, new String[]{"ឥលូវ", "ឥលូវនេះ", "ឥលូវនឹង", "ឥលូវវាម៉េច"});
        dictionary.put("alo" + CONSONANT, new String[]{"អាឡូ", "ឥលូវ", "អាឡប់"});
        dictionary.put("ban" + CONSONANT, new String[]{"បាន", "ប៉ាន", "បានហើយ", "បានសំរាក", "បានអី"});
        dictionary.put("bay" + CONSONANT, new String[]{"បាយ"});
        dictionary.put("bong" + CONSONANT, new String[]{"បង", "បងឯង", "បងស្រី", "បងខ្ញំុ", "បងស្រលាញ់"});
        dictionary.put("bat" + CONSONANT, new String[]{"បាត់", "បាត", "បាទ"});
        dictionary.put("bes" + CONSONANT, new String[]{"បេះ", "បេះដូង", "បេះបិត"});
        dictionary.put("bart" + CONSONANT, new String[]{"បាទ", "បាត", "បាតផ្សា"});
        dictionary.put("bat" + CONSONANT, new String[]{"បាទ", "បាត", "បាត់", "បាត់បង់"});
        dictionary.put("ber" + CONSONANT, new String[]{"បើ", "បើមែន", "បើអញ្ចឹមែន"});
        dictionary.put("bro" + CONSONANT, new String[]{"ប្រ", "ព្រ", "ព្រកួត", "ប្រកួតប្រជែង"});
        dictionary.put("bros" + CONSONANT, new String[]{"ប្រុស", "ប្រោស"});
        dictionary.put("choub" + CONSONANT, new String[]{"ជួប", "ចូប", "ជួបគ្នា", "ជួបជាមួយ", "ជួបបាន"});
        dictionary.put("chong" + CONSONANT, new String[]{"ចង់", "ចង", "ជង់", "ចង់បាន", "ចង់ណាស់"});
        dictionary.put("chhak" + CONSONANT, new String[]{"ឆាក", "ឈាក", "ឆាកប្រគួត"});
        dictionary.put("chhay" + CONSONANT, new String[]{"ឆាយ", "ឆ័យ", "ឆៃ"});
        dictionary.put("chet" + CONSONANT, new String[]{"ចិត្ត", "ជិត", "ចិត", "ចិត្តមេត្តា", "ជិតគ្នា"});
        dictionary.put("chmous" + CONSONANT, new String[]{"ឈ្មោះ", "ឈ្មុស"});
        dictionary.put("da" + CONSONANT, new String[]{"ដា", "ដារ", "ដាំ", "ដែរ"});
        dictionary.put("aii" + CONSONANT, new String[]{"ហើយ", "អាយ"});
        dictionary.put("arom" + CONSONANT, new String[]{"អារម្មណ៍", "អារម"});
        dictionary.put("banh" + CONSONANT, new String[]{"បាញ់", "បញ្ជាក់", "បាញ់ម៉្លេះ"});
        dictionary.put("by" + CONSONANT, new String[]{"បាយ", "ដោយ"});
        dictionary.put("bek" + CONSONANT, new String[]{"បែក", "ស្បែក", "ស្បែកជើង"});
        dictionary.put("bos" + CONSONANT, new String[]{"របស់", "បោស", "របស់របរ", "របស់ខ្ញំុ", "របស់ពីណាគេ"});
        dictionary.put("boros" + CONSONANT, new String[]{"បុរស", "ប្រុស", "មនុស្សប្រុស"});
        dictionary.put("chop" + CONSONANT, new String[]{"ឈប់", "ឈប់ទៅ", "ចប់"});
        dictionary.put("70" + CONSONANT, new String[]{"ចិត្ត", "ជិត", "ចិត", "ចិតសិប"});
        dictionary.put("chea" + CONSONANT, new String[]{"ជា", "ជៀ", "ជាតិ", "ជាមួយ"});
        dictionary.put("cham" + CONSONANT, new String[]{"ចាំ", "ចាម", "ចាំអង្កាល់", "ចាំជួប", "ចាំបាន"});
        dictionary.put("cha'oet" + CONSONANT, new String[]{"ឆ្អែត", "ឆ្អែតមែនទែន", "ឆ្អែតហើយ", "ឆ្អែតណាស់", "ឆ្អែតអត់"});
        dictionary.put("chhat" + CONSONANT, new String[]{"ឆ្អែត", "ឆ្អែតមែនទែន", "ឆ្អែតហើយ", "ឆ្អែតណាស់", "ឆ្អែតអត់"});
        dictionary.put("chaet" + CONSONANT, new String[]{"ឆ្អែត", "ឆ្អែតមែនទែន", "ឆ្អែតហើយ", "ឆ្អែតណាស់", "ឆ្អែតអត់"});
        dictionary.put("ch'et" + CONSONANT, new String[]{"ឆ្អែត", "ឆ្អែតមែនទែន", "ឆ្អែតហើយ", "ឆ្អែតណាស់", "ឆ្អែតអត់"});
        dictionary.put("ch'eart" + CONSONANT, new String[]{"ឆ្អែត", "ឆ្អែតមែនទែន", "ឆ្អែតហើយ", "ឆ្អែតណាស់", "ឆ្អែតអត់"});
        dictionary.put("chhaet" + CONSONANT, new String[]{"ឆ្អែត", "ឆ្អែតមែនទែន", "ឆ្អែតហើយ", "ឆ្អែតណាស់", "ឆ្អែតអត់"});
        dictionary.put("de" + CONSONANT, new String[]{"ដែល", "ដែរ", "ឌែល"});
        dictionary.put("del" + CONSONANT, new String[]{"ដែល", "ដែរ", "ឌែល"});
        dictionary.put("deng" + CONSONANT, new String[]{"ដឹង", "ចំណេះដឹង"});
        dictionary.put("der" + CONSONANT, new String[]{"ដើរ", "ដើរលេង", "ដើរទៅ"});
        dictionary.put("derm" + CONSONANT, new String[]{"ដើម", "ដើម្បី", "ដើមឈើ"});
        dictionary.put("dae" + CONSONANT, new String[]{"ដែល", "ដែរ", "ឌែល"});
        dictionary.put("do" + CONSONANT, new String[]{"ដូរ", "ដូ", "ធ្វើ", "ដល់", "ដ៏"});
        dictionary.put("dor" + CONSONANT, new String[]{"ដ៏", "ដ", "ដរ"});
        dictionary.put("doch" + CONSONANT, new String[]{"ដូច", "ដោច", "ដូចគ្នា", "ដូចបេះបិត", "ដូចយ៉ាងម៉េច"});
        dictionary.put("ery" + CONSONANT, new String[]{"អើយ", "អើរ"});
        dictionary.put("ey" + CONSONANT, new String[]{"អី", "អ្វី", "អីវ៉ាន់", "ឥលូវ", "អីយ៉ា"});
        dictionary.put("eyas" + CONSONANT, new String[]{"អីយ៉ាស"});
        dictionary.put("eng" + CONSONANT, new String[]{"ឯង", "អែង", "ឯងនឹង", "ឯងមែន"});
        dictionary.put("eylov" + CONSONANT, new String[]{"ឥលូវ", "ឥលូវនឹង", "ឥលូវនេះ", "ឥលូវបាន"});
        dictionary.put("ha" + CONSONANT, new String[]{"ហា", "ហាហាហា", "ហាម", "ហាង", "ហ៊ាន"});
        dictionary.put("haha" + CONSONANT, new String[]{"ហាហា", "ហាហាហា"});
        dictionary.put("heng" + CONSONANT, new String[]{"ហេង", "ហែង", "ហេងមែន", "ហេងហ្មង", "ហេងណាស់"});
        dictionary.put("jam" + CONSONANT, new String[]{"ចាំ", "ចាម", "ចាំមើល", "ចាំជួយ", "ចាំណាស់"});
        dictionary.put("jo" + CONSONANT, new String[]{"ចូល", "ចូរ", "ចូ", "ជូល"});
        dictionary.put("jol" + CONSONANT, new String[]{"ចូល", "ចូរ", "ជូល", "ចូលចិត្ត", "ចូលរៀន"});
        dictionary.put("jos" + CONSONANT, new String[]{"ចុះ", "ជុះ", "ចោះ", "ចុះឡើង"});
        dictionary.put("jong" + CONSONANT, new String[]{"ចង់", "ចង", "ចង់បាន", "ចង់អញ្ចឹង"});
        dictionary.put("jun" + CONSONANT, new String[]{"ជន", "ជន់", "ជុន", "ជូន"});
        dictionary.put("ja" + CONSONANT, new String[]{"ចា៎", "ចារ", "ចាស់", "ចារឹក", "ចាំ"});
        dictionary.put("jas" + CONSONANT, new String[]{"ចាស់", "ចា៎", "ចាស់ណាស់", "ចាស់ម៉្លេះ"});
        dictionary.put("jes" + CONSONANT, new String[]{"ចេះ", "ចេះណាស់", "ចេះតែមានហើយ"});
        dictionary.put("jet" + CONSONANT, new String[]{"ចិត្ត", "ជិត", "ចិត"});
        dictionary.put("jit" + CONSONANT, new String[]{"ចិត្ត", "ជិត", "ចិត"});
        dictionary.put("er" + CONSONANT, new String[]{"អើ", "ើ", "អើអើ", "អើយ"});
        dictionary.put("eilov" + CONSONANT, new String[]{"ឥលូវ", "ឥឡូវ"});
        dictionary.put("aluv" + CONSONANT, new String[]{"ឥលូវ", "ឥឡូវ"});
        dictionary.put("heuy" + CONSONANT, new String[]{"ហើយ", "ហើយនៅ", "ហើយវាម៉េច", "ហើយឡើងចឹង"});
        dictionary.put("houy" + CONSONANT, new String[]{"ហើយ", "ហើយនៅ", "ហើយវាម៉េច", "ហើយឡើងចឹង"});
        dictionary.put("hot" + CONSONANT, new String[]{"ហត់", "ហុត", "ក្តៅ"});
        dictionary.put("hort" + CONSONANT, new String[]{"ហត់", "ហត់ណាស់"});
        dictionary.put("hz" + CONSONANT, new String[]{"ហើយ", "ហើយនៅ", "ហើយវាម៉េច", "ហើយឡើងចឹង"});
        dictionary.put("hery" + CONSONANT, new String[]{"ហើយ", "ហើយនៅ", "ហើយវាម៉េច", "ហើយឡើងចឹង"});
        dictionary.put("herz" + CONSONANT, new String[]{"ហើយ", "ហើយនៅ", "ហើយវាម៉េច", "ហើយឡើងចឹង"});
        dictionary.put("hoy" + CONSONANT, new String[]{"ហើយ", "ហើយនៅ", "ហើយវាម៉េច", "ហើយឡើងចឹង"});
        dictionary.put("hey" + CONSONANT, new String[]{"ហើយ", "ហើយនៅ", "ហើយវាម៉េច", "ហើយឡើងចឹង"});
        dictionary.put("hy" + CONSONANT, new String[]{"ហើយ", "ហើយនៅ", "ហើយវាម៉េច", "ហើយឡើងចឹង"});
        dictionary.put("hai" + CONSONANT, new String[]{"ហើយ", "ហើយនៅ", "ហើយវាម៉េច", "ហើយឡើងចឹង"});
        dictionary.put("huy" + CONSONANT, new String[]{"ហើយ", "ហើយនៅ", "ហើយវាម៉េច", "ហើយឡើងចឹង"});
        dictionary.put("job" + CONSONANT, new String[]{"ចប់", "ចប់ហើយ", "ឈប់", "ឈប់ទៅ"});
        dictionary.put("jg" + CONSONANT, new String[]{"ចង់", "ចង", "ជង់"});
        dictionary.put("jam" + CONSONANT, new String[]{"ចាំ", "ចាម"});
        dictionary.put("ja'aet" + CONSONANT, new String[]{"ឆ្អែត", "ឆ្អែតមែនទែន", "ឆ្អែតហើយ", "ឆ្អែតណាស់", "ឆ្អែតអត់"});
        dictionary.put("jaet" + CONSONANT, new String[]{"ឆ្អែត", "ឆ្អែតមែនទែន", "ឆ្អែតហើយ", "ឆ្អែតណាស់", "ឆ្អែតអត់"});
        dictionary.put("jea" + CONSONANT, new String[]{"ជា", "ជៀ", "ជាតិ", "ជាអ្នកណា", "ជាមួយ"});
        dictionary.put("jeat" + CONSONANT, new String[]{"ជាតី", "ជៀត"});
        dictionary.put("jeang" + CONSONANT, new String[]{"ជាង", "ជាងគេ", "ជាងនេះ"});
        dictionary.put("jerng" + CONSONANT, new String[]{"ជើង", "ចើង", "ជើងស្អាត"});
        dictionary.put("joch" + CONSONANT, new String[]{"ចុច", "ជុជ", "ជុច"});
        dictionary.put("joub" + CONSONANT, new String[]{"ជួប", "ចូប", "ជួបគ្នា", "ជួបជាមួយ", "ជួបនៅណា"});
        dictionary.put("juob" + CONSONANT, new String[]{"ជួប", "ជួបគ្នា", "ជួបជាមួយ", "ជួបនៅណា"});
        dictionary.put("jbas" + CONSONANT, new String[]{"ច្បាស់", "ច្បាស់លាស់", "ច្បាស់ចែស", "ច្បាស់ណាស់"});
        dictionary.put("ka" + CONSONANT, new String[]{"ការ", "កា", "ការណ៍", "ការអី", "ការងារ"});
        dictionary.put("kam" + CONSONANT, new String[]{"កម្ម", "កាំ", "កាម", "កម្មពារ"});
        dictionary.put("kit" + CONSONANT, new String[]{"គិត", "កិត", "កឺត", "គិតអី", "គិតយ៉ាងម៉េច"});
        dictionary.put("kernh" + CONSONANT, new String[]{"ឃេីញ", "ឃើញមែន", "ឃើញហើយ", "ឃើញដែរ"});
        dictionary.put("khernh" + CONSONANT, new String[]{"ឃេីញ", "ឃើញមែន", "ឃើញហើយ", "ឃើញដែរ"});
        dictionary.put("kom" + CONSONANT, new String[]{"កំ", "កុំ", "កំពុង", "កំុចេះ", "ខំ"});
        dictionary.put("khom" + CONSONANT, new String[]{"ខំ", "ខំដែរ", "ខំទៅ", "ខំប្រឹង", "ខំហើយ"});
        dictionary.put("kda" + CONSONANT, new String[]{"ក្តារ", "ក្តារខៀន", "ក្តារចុច", "ក្តារឈ្នួន"});
        dictionary.put("knhom" + CONSONANT, new String[]{"ខ្ញុំ", "ញុំ", "ខ្ញំុជា", "ខ្ញំុស្រលាញ់អ្នក"});
        dictionary.put("ken" + CONSONANT, new String[]{"កឹន", "គេង"});
        dictionary.put("keng" + CONSONANT, new String[]{"គេង", "កេង", "គេងនៅ", "គេងហើយណា"});
        dictionary.put("kenh" + CONSONANT, new String[]{"គេញ", "គិញ", "ឃើញ"});
        dictionary.put("khoch" + CONSONANT, new String[]{"ខូច", "ខូចចិត្ត", "ខូចណាស់", "ខូចម៉្លេះ"});
        dictionary.put("khoj" + CONSONANT, new String[]{"ខូច", "ខូចចិត្ត", "ខូចណាស់", "ខូចម៉្លេះ"});
        dictionary.put("khouch" + CONSONANT, new String[]{"ខូច", "ខូចចិត្ត", "ខូចណាស់", "ខូចម៉្លេះ"});
        dictionary.put("khuch" + CONSONANT, new String[]{"ខូច", "ខូចចិត្ត", "ខូចណាស់", "ខូចម៉្លេះ"});
        dictionary.put("khuj" + CONSONANT, new String[]{"ខូច", "ខូចចិត្ត", "ខូចណាស់", "ខូចម៉្លេះ"});
        dictionary.put("khouj" + CONSONANT, new String[]{"ខូច", "ខូចចិត្ត", "ខូចណាស់", "ខូចម៉្លេះ"});
        dictionary.put("klang" + CONSONANT, new String[]{"ខ្លាំង", "ខ្លាំងម៉្លេះ", "ខ្លាំងបាត់", "ខ្ាំងម៉ង"});
        dictionary.put("knhom" + CONSONANT, new String[]{"ខ្ញុំ", "ញុំ", "ខ្ញំុជា", "ខ្ញំុស្រលាញ់"});
        dictionary.put("khnom" + CONSONANT, new String[]{"ខ្ញុំ", "ខ្ញំុមិនបាន", "ខ្ញំុជា", "ខ្ញំុស្រលាញ់"});
        dictionary.put("kh'nhom" + CONSONANT, new String[]{"ខ្ញុំ", "ខ្ញំុមិនបាន", "ខ្ញំុជា", "ខ្ញំុស្រលាញ់"});
        dictionary.put("khmer" + CONSONANT, new String[]{"ខ្មែរ", "កម្ពុជា", "ព្រះរាជាណាចក្រកម្ពុជា"});
        dictionary.put("khos" + CONSONANT, new String[]{"ខុស", "ខុសរបៀន", "ខុសមែន", "ខុសខ្លាំង"});
        dictionary.put("knea" + CONSONANT, new String[]{"គ្នា", "គ្នីគ្នា"});
        dictionary.put("knong" + CONSONANT, new String[]{"ក្នុង", "គ្នុង", "ក្នុងចិត្ត", "ក្នុងផ្ទះ"});
        dictionary.put("la" + CONSONANT, new String[]{"ឡា", "លា", "ឡាន", "លាយ", "លាក់"});
        dictionary.put("lanh" + CONSONANT, new String[]{"ស្រលាញ់", "លាញ់", "ស្រលាញ់ណាស់"});
        dictionary.put("leng" + CONSONANT, new String[]{"លេង", "ឡេង", "លេងដែរ", "លេងអត់", "លេងក៏បាន"});
        dictionary.put("ler" + CONSONANT, new String[]{"លើ", "ឡើ", "ឡើកើ"});
        dictionary.put("let" + CONSONANT, new String[]{"លិឍ", "លិទ្ធ", "លិត"});
        dictionary.put("lit" + CONSONANT, new String[]{"លិឍ", "លិទ្ធ", "លិត", "លីត្រ"});
        dictionary.put("lert" + CONSONANT, new String[]{"លិឍ", "លិទ្ធ"});
        dictionary.put("lerng" + CONSONANT, new String[]{"ឡើង", "លើង", "លិង្គ", "លឹង", "ឡឹង" });
        dictionary.put("lok" + CONSONANT, new String[]{"លោក", "ឡោក", "ឡុកឡាក់"});
        dictionary.put("lov" + CONSONANT, new String[]{"លូវ", "ឥឡូវ", "ឥលូវនឹង"});
        dictionary.put("ma" + CONSONANT, new String[]{"ម៉ា", "មា", "ម៉ាន", "ម៉ាប់", "មនុស្ស"});
        dictionary.put("man" + CONSONANT, new String[]{"មែន", "ប៉ុន្នាន", "ម៉ាន", "ម៉ាណ"});
        dictionary.put("marn" + CONSONANT, new String[]{"ប៉ុន្នាន", "ម៉ាន", "ម៉ាណ"});
        dictionary.put("men" + CONSONANT, new String[]{"មិន", "មីន", "មែន", "មិនមែន", "មែនហើយ"});
        dictionary.put("mean" + CONSONANT, new String[]{"មាន", "មៀន", "មានអី", "មានណា", "មានមែន"});
        dictionary.put("min" + CONSONANT, new String[]{"មិន", "មីន", "មិនមែន", "មិនបាន"});
        dictionary.put("mi" + CONSONANT, new String[]{"មី", "មីទៀត", "មីរហូត", "មិន"});
        dictionary.put("mes" + CONSONANT, new String[]{"ម៉េះ", "ម៉េះហ្ន៎", "ម៉្លេះ"});
        dictionary.put("me" + CONSONANT, new String[]{"មី", "មេ", "មេរៀន"});
        dictionary.put("mex" + CONSONANT, new String[]{"ម៉េច", "ម៉េចម៉ា", "ម៉េចអញ្ចឹង", "ម៉េចបាន"});
        dictionary.put("mech" + CONSONANT, new String[]{"ម៉េច", "ម៉េចម៉ា", "ម៉េចអញ្ចឹង", "ម៉េចបាន"});
        dictionary.put("mok" + CONSONANT, new String[]{"មក", "មកពីណា", "មកធ្វើអី", "មកឥលូវមក"});
        dictionary.put("mor" + CONSONANT, new String[]{"មក", "មកពីណា", "មកធ្វើអី", "មកឥលូវមក"});
        dictionary.put("mong" + CONSONANT, new String[]{"ម៉ោង", "ម៉ង", "ហ្មង"});
        dictionary.put("lit" + CONSONANT, new String[]{"លិឍ", "លិទ្ធ", "លិត", "លីត្រ"});
        dictionary.put("lork" + CONSONANT, new String[]{"លក់", "លោក", "លួក"});
        dictionary.put("luk" + CONSONANT, new String[]{"លក់", "លូក"});
        dictionary.put("louk" + CONSONANT, new String[]{"លក់", "លូក", "លក់ម៉ង"});
        dictionary.put("merl" + CONSONANT, new String[]{"មើល", "មើលអី", "មើលគេ", "មើលវា"});
        dictionary.put("mer" + CONSONANT, new String[]{"មើល", "មើលអី", "មើលគេ", "មើលវា"});
        dictionary.put("mel" + CONSONANT, new String[]{"មើល", "មើលអី", "មើលគេ", "មើលវា"});
        dictionary.put("m'nous" + CONSONANT, new String[]{"មនុស្ស", "មនុស្សយើង", "មនុស្សម្នា"});
        dictionary.put("m'nus" + CONSONANT, new String[]{"មនុស្ស", "មនុស្សយើង", "មនុស្សម្នា"});
        dictionary.put("manos" + CONSONANT, new String[]{"មនុស្ស", "មនុស្សយើង", "មនុស្សម្នា"});
        dictionary.put("manuz" + CONSONANT, new String[]{"មនុស្ស", "មនុស្សយើង", "មនុស្សម្នា"});
        dictionary.put("mnos" + CONSONANT, new String[]{"មនុស្ស", "មនុស្សយើង", "មនុស្សម្នា"});
        dictionary.put("mnous" + CONSONANT, new String[]{"មនុស្ស", "មនុស្សយើង", "មនុស្សម្នា"});
        dictionary.put("mnus" + CONSONANT, new String[]{"មនុស្ស", "មនុស្សយើង", "មនុស្សម្នា"});
        dictionary.put("monos" + CONSONANT, new String[]{"មនុស្ស", "មនុស្សយើង", "មនុស្សម្នា"});
        dictionary.put("monus" + CONSONANT, new String[]{"មនុស្ស", "មនុស្សយើង", "មនុស្សម្នា"});
        dictionary.put("munus" + CONSONANT, new String[]{"មនុស្ស", "មនុស្សយើង", "មនុស្សម្នា"});
        dictionary.put("mung" + CONSONANT, new String[]{"មុង"});
        dictionary.put("muk" + CONSONANT, new String[]{"មុខ", "មុគ", "មុក", "មក", "មុខចាស់"});
        dictionary.put("mjas" + CONSONANT, new String[]{"ម្ចាស់", "ម្ចាស់ចិត្ត", "ម្ចាស់ផ្ទះ", "ម្ចាស់ជំនួយ"});
        dictionary.put("mnak" + CONSONANT, new String[]{"ម្នាក់", "មួយនាក់", "ម្នាក់នេះ", "ម្នាក់ហ្នឹង"});
        dictionary.put("ml" + CONSONANT, new String[]{"សម្លាញ់", "សម្លាញ់ចិត្ត", "សម្លាញ់យើង"});
        dictionary.put("mlanh" + CONSONANT, new String[]{"សម្លាញ់", "សម្លាញ់ចិត្ត", "សម្លាញ់យើង"});
        dictionary.put("nhom" + CONSONANT, new String[]{"ខ្ញុំ", "ញុំ", "ខ្ញំុស្រលាញ់", "ខ្ញំុចូលចិត្ត"});
        dictionary.put("nham" + CONSONANT, new String[]{"ញាំុ", "ញាំុបាយ", "ញាំុអី", "ញាំុអត់"});
        dictionary.put("na" + CONSONANT, new String[]{"ណា", "ណា៎","ណាគេនឹង", "ណាក៏បាន"});
        dictionary.put("nak" + CONSONANT, new String[]{"នាក់", "ណាក់", "ម្នាក់", "នាក់ខ្លះ"});
        dictionary.put("nas" + CONSONANT, new String[]{"ណាស់", "ណាស់ណា"});
        dictionary.put("nerk" + CONSONANT, new String[]{"នឹក", "នឹកណាស់", "នឹកមែន", "នឹកខ្លាំង"});
        dictionary.put("neng" + CONSONANT, new String[]{"ហ្នឹង", "និង", "នឹង", "នឹងហើយ"});
        dictionary.put("nov" + CONSONANT, new String[]{"នៅ", "នូវ", "នៅណា", "នៅរៀន", "នៅផ្ទះ"});
        dictionary.put("nis" + CONSONANT, new String[]{"នេះ", "នោះ", "នេះនែ", "នេះជា"});
        dictionary.put("nos" + CONSONANT, new String[]{"នោះ", "នេះ", "នោះអី"});
        dictionary.put("nus" + CONSONANT, new String[]{"នោះ", "នេះ", "នោះអី", "នោះមែន"});
        dictionary.put("or" + CONSONANT, new String[]{"អ", "អរ", "អរអី"});
        dictionary.put("os" + CONSONANT, new String[]{"អស់", "អូស", "អស់អី", "អស់រលីង", "អស់ប៉ុន្មាន"});
        dictionary.put("oy" + CONSONANT, new String[]{"អោយ", "អូយ", "អយ", "អោយតែ", "អោមក"});
        dictionary.put("ot" + CONSONANT, new String[]{"អត់", "អត់ទេ", "អត់មាន", "អត់បាន"});
        dictionary.put("ort" + CONSONANT, new String[]{"អត់", "អត់ទេ", "អត់មាន", "អត់បាន"});
        dictionary.put("oun" + CONSONANT, new String[]{"អូន","អូនឯង", "អូនស្រី", "អូនតូច"});
        dictionary.put("pong" + CONSONANT, new String[]{"ផង", "ផង់", "ភង់", "កំពុង"});
        dictionary.put("pel" + CONSONANT, new String[]{"ពេល", "ពែ", "ពេលរេលា", "ពេលណា", "ពេលណាក៏បាន"});
        dictionary.put("pean" + CONSONANT, new String[]{"ពាន", "ពានរង្វាន់", "ភាន"});
        dictionary.put("pov" + CONSONANT, new String[]{"ពៅ", "ប៉ូវ", "ពៅព្រលឺង", "ពៅមាស"});
        dictionary.put("phong" + CONSONANT, new String[]{"ផង", "ផង់", "ភង់"});
        dictionary.put("pun" + CONSONANT, new String[]{"ពន្ធ", "ប៉ុន", "ពុន", "ពុង"});
        dictionary.put("nhae" + CONSONANT, new String[]{"ញែរ", "ញែរនាង", "ញែរគាត់"});
        dictionary.put("naz" + CONSONANT, new String[]{"ណាស់", "ណាស់ណា"});
        dictionary.put("nek" + CONSONANT, new String[]{"អ្នក", "នាក់", "នឹក"});
        dictionary.put("nik" + CONSONANT, new String[]{"នឹក", "នឹកណាស់", "នឹកគេ", "នឹកមែន", "នឹកគ្រប់ពេល"});
        dictionary.put("noek" + CONSONANT, new String[]{"នឹក", "នឹកណាស់", "នឹកគេ", "នឹកមែន", "នឹកគ្រប់ពេល"});
        dictionary.put("nirk" + CONSONANT, new String[]{"នឹក", "នឹកណាស់", "នឹកគេ", "នឹកមែន", "នឹកគ្រប់ពេល"});
        dictionary.put("neok" + CONSONANT, new String[]{"នឹក", "នឹកណាស់", "នឹកគេ", "នឹកមែន", "នឹកគ្រប់ពេល"});
        dictionary.put("ngob" + CONSONANT, new String[]{"ងាប់", "ងាប់ហើយ", "ងាប់បាត់", "ងាប់ទៅ"});
        dictionary.put("ngai" + CONSONANT, new String[]{"ថ្ងៃ", "ថ្ងៃនេះ", "ថ្ងៃស្អែក"});
        dictionary.put("ngea" + CONSONANT, new String[]{"ងារ", "ងា", "ងាយ", "ងាយណាស់"});
        dictionary.put("ngeay" + CONSONANT, new String[]{"ងាយ", "ងាយស្រួល", "ងាយណាស់"});
        dictionary.put("om" + CONSONANT, new String[]{"អំ", "អម", "អំពី"});
        dictionary.put("pi" + CONSONANT, new String[]{"ពី", "ពីរ", "អំពី"});
        dictionary.put("phirk" + CONSONANT, new String[]{"ផឹក", "ផឹកទឹក", "ផឹកស្រា", "ផឹកនៅណា", "ផឹកអី"});
        dictionary.put("pherk" + CONSONANT, new String[]{"ផឹក", "ផឹកទឹក", "ផឹកស្រា", "ផឹកនៅណា", "ផឹកអី"});
        dictionary.put("phek" + CONSONANT, new String[]{"ផឹក", "ផឹកទឹក", "ផឹកស្រា", "ផឹកនៅណា", "ផឹកអី"});
        dictionary.put("pro" + CONSONANT, new String[]{"ប្រ", "ព្រ", "ព្រកួត", "ព្រហែល", "ព្រកួតប្រជែង"});
        dictionary.put("prolong" + CONSONANT, new String[]{"ប្រឡង", "ប្រលង", "ប្រលងហើយ", "ប្រលងពេលណា"});
        dictionary.put("brolong" + CONSONANT, new String[]{"ប្រឡង", "ប្រលង", "ប្រលងហើយ", "ប្រលងពេលណា"});
        dictionary.put("plong" + CONSONANT, new String[]{"ប្រឡង", "ប្រលង", "ប្រលងហើយ", "ប្រលងពេលណា"});
        dictionary.put("pros" + CONSONANT, new String[]{"ប្រុស", "ប្រោស", "ព្រោះ", "ព្រោះថា"});
        dictionary.put("riel" + CONSONANT, new String[]{"៛", "រៀល"});
        dictionary.put("rean" + CONSONANT, new String[]{"រៀន", "រៀនសូត្រ", "រៀននៅណា", "រៀនទៅ"});
        dictionary.put("ra" + CONSONANT, new String[]{"រា", "រ៉ា", "រាប់"});
        dictionary.put("sa" + CONSONANT, new String[]{"សា", "សារ", "សារភាព", "សប្បាយ", "សាសនា"});
        dictionary.put("s'ek" + CONSONANT, new String[]{"ស្អែក", "ស្អែកនេះ", "ស្អែកបានអត់", "ស្អែករវល់"});
        dictionary.put("tmr" + CONSONANT, new String[]{"ស្អែក", "ស្អែកនេះ", "ស្អែកបានអត់", "ស្អែករវល់"});
        dictionary.put("saek" + CONSONANT, new String[]{"ស្អែក", "ស្អែកនេះ", "ស្អែកបានអត់", "ស្អែករវល់"});
        dictionary.put("tomorrow" + CONSONANT, new String[]{"ស្អែក", "ស្អែកនេះ", "ស្អែកបានអត់", "ស្អែករវល់"});
        dictionary.put("ror" + CONSONANT, new String[]{"រ", "រក", "របៀប", "រវល់", "រង"});
        dictionary.put("sarn" + CONSONANT, new String[]{"សាន្ត", "សាន"});
        dictionary.put("sab" + CONSONANT, new String[]{"សព្ទ", "សាប", "សាប់", "សាព"});
        dictionary.put("sala" + CONSONANT, new String[]{"សាលា", "សាលារៀន", "សាលាពេទ្យ", "សាលាទាហាន"});
        dictionary.put("seksa" + CONSONANT, new String[]{"សិក្សា"});
        dictionary.put("sabay" + CONSONANT, new String[]{"សប្បាយ","សប្បាយចិត្ត","សប្បាយណាស់"});
        dictionary.put("sbay" + CONSONANT, new String[]{"សប្បាយ","សប្បាយចិត្ត","សប្បាយណាស់"});
        dictionary.put("sart" + CONSONANT, new String[]{"ស្អាត", "ស្អាតបាត"});
        dictionary.put("saart" + CONSONANT, new String[]{"ស្អាត", "ស្អាតបាត"});
        dictionary.put("sok" + CONSONANT, new String[]{"សុខ", "សុក", "សុខសប្បាយ"});
        dictionary.put("saby" + CONSONANT, new String[]{"សប្បាយ","សប្បាយចិត្ត","សប្បាយណាស់"});
        dictionary.put("so" + CONSONANT, new String[]{"សុ", "សូ", "សុភាព", "សុខ", "សុក"});
        dictionary.put("song" + CONSONANT, new String[]{"សង", "សង់", "សង្ឃ", "សង្ខេប", "សង្ខ"});
        dictionary.put("sork" + CONSONANT, new String[]{"សក់", "សក"});
        dictionary.put("som" + CONSONANT, new String[]{"សម", "សុំ", "សម្លាញ់"});
        dictionary.put("somlanh" + CONSONANT, new String[]{"សម្លាញ់", "សម្លាញ់ចិត្ត", "សម្លាញ់យើង"});
        dictionary.put("sos" + CONSONANT, new String[]{"សោះ", "សស់", "សរសើរ"});
        dictionary.put("sopheab" + CONSONANT, new String[]{"សុភាព", "សុភាពរាបសារ", "សុភាពទន់ភ្លន់"});
        dictionary.put("sopheap" + CONSONANT, new String[]{"សុភាព", "សុភាពរាបសារ", "សុភាពទន់ភ្លន់"});
        dictionary.put("srey" + CONSONANT, new String[]{"ស្រី", "ស្រីស្អាត", "ស្រស់ថ្លា", "ស្ស់ម៉្លេះ"});
        dictionary.put("sros" + CONSONANT, new String[]{"ស្រី", "ស្រីស្អាត", "ស្រស់ថ្លា", "ស្ស់ម៉្លេះ"});
        dictionary.put("sroul" + CONSONANT, new String[]{"ស្រួល", "ងាយស្រួល", "ស្រួលណាស់"});
        dictionary.put("sbek" + CONSONANT, new String[]{"ស្បែក", "ស្បេក", "ស្បែកជើង"});
        dictionary.put("ta" + CONSONANT, new String[]{"តែ", "តា", "តាមាន", "តាម"});
        dictionary.put("tas" + CONSONANT, new String[]{"ផ្ទះ", "តាស៎", "ទះ"});
        dictionary.put("ptas" + CONSONANT, new String[]{"ផ្ទះ", "ផ្ទះបាយ", "ផ្ទះឈើ"});
        dictionary.put("psar" + CONSONANT, new String[]{"ផ្សារ", "ផ្សា", "ផ្សារជិតនឹង", "ផ្សារទំនើប"});
        dictionary.put("tha" + CONSONANT, new String[]{"ថា", "ធា", "ថាលេង", "ថាម៉េច", });
        dictionary.put("thea" + CONSONANT, new String[]{"ធា", "ធៀ", "ធានា", "ធៀប"});
        dictionary.put("tam" + CONSONANT, new String[]{"តាម", "ថាម", "តាមណា", "តាមនឹង", "តាមគ្នា"});
        dictionary.put("tang" + CONSONANT, new String[]{"ទាំង", "តាង", "តាំង", "ទាំងអស់", "តាំងអញ្ចឹងទៀតហើយ"});
        dictionary.put("te" + CONSONANT, new String[]{"ទេ", "តេ", "តែ"});
        dictionary.put("ter" + CONSONANT, new String[]{"តើ", "ធ្វើ", "តែ", "តែមាន"});
        dictionary.put("tver" + CONSONANT, new String[]{"ធ្វើ", "ធ្វើអី", "ធ្វើហើយ"});
        dictionary.put("tae" + CONSONANT, new String[]{"តែ", "ទេ", "តេ", "តែមាន"});
        dictionary.put("tes" + CONSONANT, new String[]{"ទះ", "ផ្ទះ", "ទេសនា", "ទេះ"});
        dictionary.put("team" + CONSONANT, new String[]{"ទាម", "ទាមទារ"});
        dictionary.put("temta" + CONSONANT, new String[]{"ទើបតែ"});
        dictionary.put("tea" + CONSONANT, new String[]{"ទា", "ទារ", "តៀរ"});
        dictionary.put("tov" + CONSONANT, new String[]{"ទៅ", "ទៅណា", "ទៅដែរ", "ទៅធ្វើអី"});
        dictionary.put("tol" + CONSONANT, new String[]{"ទាល់", "ទល់", "ទាល់តែ", "ទល់នឹង"});
        dictionary.put("trov" + CONSONANT, new String[]{"ត្រូវ", "ត្រូវតែ", "ត្រូវខំ", "ត្រូវទៅ", "ត្រូវហើយ"});
        dictionary.put("tu" + CONSONANT, new String[]{"ទូ", "ទូរ", "ទូរស័ព្ទ", "ទូរទស្សន៍", "ទូរ"});
        dictionary.put("tngai" + CONSONANT, new String[]{"ថ្ងៃ", "ថ្ងៃនេះ", "ថ្ងៃថ្មី", "ថ្ងៃហើយ"});
        dictionary.put("tmey" + CONSONANT, new String[]{"ថ្មី", "ថ្មីថ្មោង", "ថ្មីមែន", "ថ្មីម៉េះ", "ថ្មីណាស់"});
        dictionary.put("thmey" + CONSONANT, new String[]{"ថ្មី", "ថ្មីថ្មោង", "ថ្មីមែន", "ថ្មីម៉េះ", "ថ្មីណាស់"});
        dictionary.put("tlai" + CONSONANT, new String[]{"ថ្លៃ", "ថ្លៃណាស់", "ថ្លៃមែនទែន"});
        dictionary.put("thlai" + CONSONANT, new String[]{"ថ្លៃ", "ថ្លៃណាស់", "ថ្លៃមែនទែន"});
        dictionary.put("vea" + CONSONANT, new String[]{"វា", "វៀ", "វារ", "វានឹង", "វាមិនដែល"});
        dictionary.put("vinh" + CONSONANT, new String[]{"វិញ", "វេញ", "វិញហើយ"});
        dictionary.put("venh" + CONSONANT, new String[]{"វិញ", "វេញ", "វិញហើយ"});
        dictionary.put("very" + CONSONANT, new String[]{"វើយ", "វាយ"});
        dictionary.put("vouk" + CONSONANT, new String[]{"វក់", "វក់វី", "វក់ហ្មង", "វក់ម៉េះ"});
        dictionary.put("vi" + CONSONANT, new String[]{"វី", "វីរ", "វីវរ", "វីវក់", "វិទ្យាស្ថាន"});
        dictionary.put("yor" + CONSONANT, new String[]{"យក", "យកមក", "យកអីនឹង", "យកក៏បាន", "យកមួយណា"});
        dictionary.put("yok" + CONSONANT, new String[]{"យក", "យកមក", "យកអីនឹង", "យកក៏បាន", "យកមួយណា"});
        dictionary.put("yerng" + CONSONANT, new String[]{"យើង", "យើងនឺង", "យើងមាន", "យើងទាំងអស់គ្នា"});
        dictionary.put("yu" + CONSONANT, new String[]{"យូរ", "យូរណាស់", "យូរយារ", "យូរមែនទែន"});
        dictionary.put("terk" + CONSONANT, new String[]{"ទឹក", "ទឹកផឹក", "ទឹកទន្លេ"});
        dictionary.put("tek" + CONSONANT, new String[]{"ទឹក", "ទឹកផឹក", "ទឹកទន្លេ", "ទាក់ទង"});
        dictionary.put("tak" + CONSONANT, new String[]{"ទាក់", "ទាក់ទង", "តាក់", "តាក់តែង"});
        dictionary.put("teuk" + CONSONANT, new String[]{"ទឹក", "ទឹកផឹក", "ទឹកទន្លេ", "ទឹកភ្លៀង"});
        dictionary.put("tnak" + CONSONANT, new String[]{"ថ្នាក់", "ថ្នាក់នឹង", "ថ្នាក់រៀន"});
        dictionary.put("tlak" + CONSONANT, new String[]{"ថ្លាក់", "ថ្លាក់បាត់", "ថ្លាក់ទៀត", "ថ្លាក់រហូត"});
        dictionary.put("tok" + CONSONANT, new String[]{"តុ", "ទុក", "ទុកដាក់", "ទក់"});
        dictionary.put("tuk" + CONSONANT, new String[]{"តុ", "ទុក", "ទុកដាក់", "ទក់"});
        dictionary.put("to" + CONSONANT, new String[]{"ថូ", "ធូរ", "ទៅ", "ធូរៈ"});
        dictionary.put("tong" + CONSONANT, new String[]{"តង់", "តោង", "ទង", "ថង់"});
        dictionary.put("thu" + CONSONANT, new String[]{"ធូរ", "ថូ", "ធូរៈ"});
        dictionary.put("vai" + CONSONANT, new String[]{"វាយ", "វៃ", "វាយគ្នា"});
        dictionary.put("yol" + CONSONANT, new String[]{"យល់", "យល់ចិត្ត", "យល់គ្នា"});
        dictionary.put("yul" + CONSONANT, new String[]{"យល់", "យល់ចិត្ត", "យល់គ្នា"});

        dictionary.put("tech" + CONSONANT, new String[]{"តិច", "បន្តិច", "តិចទៀត", "តិចណាស់"});
        dictionary.put("ti" + CONSONANT, new String[]{"ទី", "ទីណា", "ទីស្រលាញ់", "តិចណាស់"});
        dictionary.put("tic" + CONSONANT, new String[]{"តិច", "បន្តិច", "តិចទៀត", "តិចណាស់"});
        dictionary.put("teat" + CONSONANT, new String[]{"ទៀត", "ទៀតហើយ", ""});
        dictionary.put("pleang" + CONSONANT, new String[]{"ភ្លៀង", "ភ្លៀងបាត់", "ភ្លៀងខ្លាំង", "ភ្លៀងអត់"});
        dictionary.put("mek" + CONSONANT, new String[]{"មេឃ", "មេឃភ្លៀង", "មេឃងងឹត", "មេឃពណ៍ខៀវ"});
        dictionary.put("sror" + CONSONANT, new String[]{"ស្រ", "ស្រី", "ស្រលាញ់", "ស្រលះ"});
        dictionary.put("yeay" + CONSONANT, new String[]{"និយាយ", "យាយ", "និយាយថា", "និយាយបានល្អ"});
        dictionary.put("niyeay" + CONSONANT, new String[]{"និយាយ", "យាយ", "និយាយថា", "និយាយបានល្អ"});
        dictionary.put("hean" + CONSONANT, new String[]{"ហ៊ាន", "ហ៊ានណាស់", "ហ៊ានទៅ", "ហ៊ានម់្លេះ"});
        dictionary.put("sur" + CONSONANT, new String[]{"សួរ", "សួរស្តី", "សួរអី", "សួរម៉ោ"});
        dictionary.put("sdey" + CONSONANT, new String[]{"ស", "សួរស្តី", "សួរអី", "សួរម៉ោ"});
        dictionary.put("sdei" + CONSONANT, new String[]{"ស្តី", "ស្តីឲ្យ"});
        dictionary.put("sday" + CONSONANT, new String[]{"ស្តាយ", "ស្តាយក្រោយ", "ស្តាយហ្មង", "ស្តាយណាស់"});
        dictionary.put("loy" + CONSONANT, new String[]{"ឡូយ", "ឡូយម៉េះ", "ឡូយមែនទែន", "ឡូយហ្មង"});
        dictionary.put("luy" + CONSONANT, new String[]{"លុយ", "លុយចាយ", "លុយខ្មែរ", "លុយដុល្លារ"});
        dictionary.put("ney" + CONSONANT, new String[]{"ន័យ", "ន័យណាស់", "ន័យថាម៉េច", "ន័យល្អ"});
        dictionary.put("nei" + CONSONANT, new String[]{"ន័យ", "ន័យណាស់", "ន័យថាម៉េច", "ន័យល្អ"});






        //COMMON TRANSLATION PART INITIALIZATION
        dictionary.put("name" + CONSONANT, new String[]{"ឈ្មោះ"});
        dictionary.put("okay" + CONSONANT, new String[]{"បាទ", "ចា៎", "អូខេ"});
        dictionary.put("yummy" + CONSONANT, new String[]{"ឆ្ងាញ់", "រសជាតីឆ្ងាញ់"});
        dictionary.put("see" + CONSONANT, new String[]{"ឃេីញ", "ឃើញមែន", "ឃើញហើយ", "ឃើញដែរ"});

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
