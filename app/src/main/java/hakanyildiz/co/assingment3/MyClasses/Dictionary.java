package hakanyildiz.co.assingment3.MyClasses;

/**
 * Created by hakan on 23.03.2016.
 */
public class Dictionary {
    int id;
    String turkishWord;
    String englishWord;

    public Dictionary()
    {

    }

    public Dictionary(String turkishWord, String englishWord)
    {
        this.turkishWord = turkishWord;
        this.englishWord = englishWord;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public void setTurkishWord(String turkishWord) {
        this.turkishWord = turkishWord;
    }

    public int getId() {
        return id;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public String getTurkishWord() {
        return turkishWord;
    }
}
