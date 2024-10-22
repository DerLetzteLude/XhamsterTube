package dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table XHAMSTER_COMMENT.
*/
public class XhamsterCommentDao extends AbstractDao<XhamsterComment, Long> {

    public static final String TABLENAME = "XHAMSTER_COMMENT";

    /**
     * Properties of entity XhamsterComment.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserName = new Property(1, String.class, "userName", false, "USER_NAME");
        public final static Property UserUrl = new Property(2, String.class, "userUrl", false, "USER_URL");
        public final static Property ThumbUrl = new Property(3, String.class, "thumbUrl", false, "THUMB_URL");
        public final static Property Text = new Property(4, String.class, "text", false, "TEXT");
        public final static Property Postedtime = new Property(5, String.class, "postedtime", false, "POSTEDTIME");
        public final static Property Replylink = new Property(6, String.class, "replylink", false, "REPLYLINK");
        public final static Property Usergender = new Property(7, String.class, "usergender", false, "USERGENDER");
        public final static Property Usernationality = new Property(8, String.class, "usernationality", false, "USERNATIONALITY");
        public final static Property Messagelink = new Property(9, String.class, "messagelink", false, "MESSAGELINK");
    };


    public XhamsterCommentDao(DaoConfig config) {
        super(config);
    }
    
    public XhamsterCommentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'XHAMSTER_COMMENT' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'USER_NAME' TEXT," + // 1: userName
                "'USER_URL' TEXT," + // 2: userUrl
                "'THUMB_URL' TEXT," + // 3: thumbUrl
                "'TEXT' TEXT," + // 4: text
                "'POSTEDTIME' TEXT," + // 5: postedtime
                "'REPLYLINK' TEXT," + // 6: replylink
                "'USERGENDER' TEXT," + // 7: usergender
                "'USERNATIONALITY' TEXT," + // 8: usernationality
                "'MESSAGELINK' TEXT);"); // 9: messagelink
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'XHAMSTER_COMMENT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, XhamsterComment entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(2, userName);
        }
 
        String userUrl = entity.getUserUrl();
        if (userUrl != null) {
            stmt.bindString(3, userUrl);
        }
 
        String thumbUrl = entity.getThumbUrl();
        if (thumbUrl != null) {
            stmt.bindString(4, thumbUrl);
        }
 
        String text = entity.getText();
        if (text != null) {
            stmt.bindString(5, text);
        }
 
        String postedtime = entity.getPostedtime();
        if (postedtime != null) {
            stmt.bindString(6, postedtime);
        }
 
        String replylink = entity.getReplylink();
        if (replylink != null) {
            stmt.bindString(7, replylink);
        }
 
        String usergender = entity.getUsergender();
        if (usergender != null) {
            stmt.bindString(8, usergender);
        }
 
        String usernationality = entity.getUsernationality();
        if (usernationality != null) {
            stmt.bindString(9, usernationality);
        }
 
        String messagelink = entity.getMessagelink();
        if (messagelink != null) {
            stmt.bindString(10, messagelink);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public XhamsterComment readEntity(Cursor cursor, int offset) {
        XhamsterComment entity = new XhamsterComment( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userUrl
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // thumbUrl
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // text
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // postedtime
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // replylink
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // usergender
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // usernationality
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // messagelink
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, XhamsterComment entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserUrl(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setThumbUrl(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setText(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPostedtime(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setReplylink(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setUsergender(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setUsernationality(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setMessagelink(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(XhamsterComment entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(XhamsterComment entity) {
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
    
}
