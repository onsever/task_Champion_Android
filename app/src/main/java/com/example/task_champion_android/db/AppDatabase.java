package com.example.task_champion_android.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                Category.class,
                Item.class,
                MediaItem.class,
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public static volatile AppDatabase INSTANCE;
    public abstract QueryDao queryDao();
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .addCallback(AppRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static final RoomDatabase.Callback AppRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                       databaseWriteExecutor.execute(() -> {
                        QueryDao queryDao = INSTANCE.queryDao();
                        Category category1 = new Category("Home");
                        long catId1 = queryDao.insertCategory(category1);
                        Item item1 = new Item("testing1", catId1, "testing details", "-NA-");
                        long itemId1 = queryDao.insertItem(item1);
                        MediaItem mediaItem1 = new MediaItem("testing1", "/1/1221",itemId1,MediaItem.Type.AUDIO);
                        MediaItem mediaItem2 = new MediaItem("testing2", "/1/1221",itemId1,MediaItem.Type.AUDIO);
                        queryDao.insertMediaItem(mediaItem1);
                        queryDao.insertMediaItem(mediaItem2);
                        Category category2 = new Category("Business");
                        queryDao.insertCategory(category2);
                    });
                }
            };
}
