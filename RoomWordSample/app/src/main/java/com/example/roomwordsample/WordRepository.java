package com.example.roomwordsample;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application) {
        WordRoomDatabse db = WordRoomDatabse.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }

    LiveData<List<Word>> getmAllWords () {
        return mAllWords;
    }

    void insert(Word word) {
        WordRoomDatabse.databaseWriterExecutor.execute(() -> {
            mWordDao.insert(word);
        });
    }
}
