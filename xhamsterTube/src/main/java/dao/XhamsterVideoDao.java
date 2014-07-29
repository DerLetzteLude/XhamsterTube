package dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table XHAMSTER_VIDEO.
*/
public class XhamsterVideoDao extends AbstractDao<XhamsterVideo, Long> {

    public static final String TABLENAME = "XHAMSTER_VIDEO";

    /**
     * Properties of entity XhamsterVideo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property SiteUrl = new Property(2, String.class, "siteUrl", false, "SITE_URL");
        public final static Property StreamUrl = new Property(3, String.class, "streamUrl", false, "STREAM_URL");
        public final static Property Description = new Property(4, String.class, "description", false, "DESCRIPTION");
        public final static Property UserName = new Property(5, String.class, "userName", false, "USER_NAME");
        public final static Property UserURL = new Property(6, String.class, "userURL", false, "USER_URL");
        public final static Property Runtime = new Property(7, String.class, "Runtime", false, "RUNTIME");
        public final static Property ViewCount = new Property(8, String.class, "viewCount", false, "VIEW_COUNT");
        public final static Property ThumbUrl = new Property(9, String.class, "thumbUrl", false, "THUMB_URL");
        public final static Property CommentCount = new Property(10, Integer.class, "commentCount", false, "COMMENT_COUNT");
        public final static Property AddedTime = new Property(11, String.class, "addedTime", false, "ADDED_TIME");
        public final static Property Likes = new Property(12, String.class, "likes", false, "LIKES");
        public final static Property Dislikes = new Property(13, String.class, "dislikes", false, "DISLIKES");
        public final static Property Rating = new Property(14, String.class, "rating", false, "RATING");
        public final static Property Category1 = new Property(15, String.class, "category1", false, "CATEGORY1");
        public final static Property Category2 = new Property(16, String.class, "category2", false, "CATEGORY2");
        public final static Property Category3 = new Property(17, String.class, "category3", false, "CATEGORY3");
        public final static Property Category4 = new Property(18, String.class, "category4", false, "CATEGORY4");
        public final static Property Category5 = new Property(19, String.class, "category5", false, "CATEGORY5");
        public final static Property XhamsterUserId = new Property(20, long.class, "xhamsterUserId", false, "XHAMSTER_USER_ID");
    };

    private Query<XhamsterVideo> xhamsterUser_XhamsterVideoListQuery;

    public XhamsterVideoDao(DaoConfig config) {
        super(config);
    }
    
    public XhamsterVideoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'XHAMSTER_VIDEO' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'TITLE' TEXT," + // 1: title
                "'SITE_URL' TEXT," + // 2: siteUrl
                "'STREAM_URL' TEXT," + // 3: streamUrl
                "'DESCRIPTION' TEXT," + // 4: description
                "'USER_NAME' TEXT," + // 5: userName
                "'USER_URL' TEXT," + // 6: userURL
                "'RUNTIME' TEXT," + // 7: Runtime
                "'VIEW_COUNT' TEXT," + // 8: viewCount
                "'THUMB_URL' TEXT," + // 9: thumbUrl
                "'COMMENT_COUNT' INTEGER," + // 10: commentCount
                "'ADDED_TIME' TEXT," + // 11: addedTime
                "'LIKES' TEXT," + // 12: likes
                "'DISLIKES' TEXT," + // 13: dislikes
                "'RATING' TEXT," + // 14: rating
                "'CATEGORY1' TEXT," + // 15: category1
                "'CATEGORY2' TEXT," + // 16: category2
                "'CATEGORY3' TEXT," + // 17: category3
                "'CATEGORY4' TEXT," + // 18: category4
                "'CATEGORY5' TEXT," + // 19: category5
                "'XHAMSTER_USER_ID' INTEGER NOT NULL );"); // 20: xhamsterUserId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'XHAMSTER_VIDEO'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, XhamsterVideo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String siteUrl = entity.getSiteUrl();
        if (siteUrl != null) {
            stmt.bindString(3, siteUrl);
        }
 
        String streamUrl = entity.getStreamUrl();
        if (streamUrl != null) {
            stmt.bindString(4, streamUrl);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(5, description);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(6, userName);
        }
 
        String userURL = entity.getUserURL();
        if (userURL != null) {
            stmt.bindString(7, userURL);
        }
 
        String Runtime = entity.getRuntime();
        if (Runtime != null) {
            stmt.bindString(8, Runtime);
        }
 
        String viewCount = entity.getViewCount();
        if (viewCount != null) {
            stmt.bindString(9, viewCount);
        }
 
        String thumbUrl = entity.getThumbUrl();
        if (thumbUrl != null) {
            stmt.bindString(10, thumbUrl);
        }
 
        Integer commentCount = entity.getCommentCount();
        if (commentCount != null) {
            stmt.bindLong(11, commentCount);
        }
 
        String addedTime = entity.getAddedTime();
        if (addedTime != null) {
            stmt.bindString(12, addedTime);
        }
 
        String likes = entity.getLikes();
        if (likes != null) {
            stmt.bindString(13, likes);
        }
 
        String dislikes = entity.getDislikes();
        if (dislikes != null) {
            stmt.bindString(14, dislikes);
        }
 
        String rating = entity.getRating();
        if (rating != null) {
            stmt.bindString(15, rating);
        }
 
        String category1 = entity.getCategory1();
        if (category1 != null) {
            stmt.bindString(16, category1);
        }
 
        String category2 = entity.getCategory2();
        if (category2 != null) {
            stmt.bindString(17, category2);
        }
 
        String category3 = entity.getCategory3();
        if (category3 != null) {
            stmt.bindString(18, category3);
        }
 
        String category4 = entity.getCategory4();
        if (category4 != null) {
            stmt.bindString(19, category4);
        }
 
        String category5 = entity.getCategory5();
        if (category5 != null) {
            stmt.bindString(20, category5);
        }
        stmt.bindLong(21, entity.getXhamsterUserId());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public XhamsterVideo readEntity(Cursor cursor, int offset) {
        XhamsterVideo entity = new XhamsterVideo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // siteUrl
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // streamUrl
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // description
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // userName
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // userURL
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // Runtime
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // viewCount
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // thumbUrl
            cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10), // commentCount
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // addedTime
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // likes
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // dislikes
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // rating
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // category1
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // category2
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // category3
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // category4
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // category5
            cursor.getLong(offset + 20) // xhamsterUserId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, XhamsterVideo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSiteUrl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setStreamUrl(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDescription(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUserName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setUserURL(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setRuntime(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setViewCount(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setThumbUrl(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCommentCount(cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10));
        entity.setAddedTime(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setLikes(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setDislikes(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setRating(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setCategory1(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setCategory2(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setCategory3(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setCategory4(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setCategory5(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setXhamsterUserId(cursor.getLong(offset + 20));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(XhamsterVideo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(XhamsterVideo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "xhamsterVideoList" to-many relationship of XhamsterUser. */
    public List<XhamsterVideo> _queryXhamsterUser_XhamsterVideoList(long xhamsterUserId) {
        synchronized (this) {
            if (xhamsterUser_XhamsterVideoListQuery == null) {
                QueryBuilder<XhamsterVideo> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.XhamsterUserId.eq(null));
                xhamsterUser_XhamsterVideoListQuery = queryBuilder.build();
            }
        }
        Query<XhamsterVideo> query = xhamsterUser_XhamsterVideoListQuery.forCurrentThread();
        query.setParameter(0, xhamsterUserId);
        return query.list();
    }

}
