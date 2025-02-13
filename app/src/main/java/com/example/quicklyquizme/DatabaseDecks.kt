package com.example.quicklyquizme

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseDecks (private val context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery=("CREATE TABLE  $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_DECK_NAME TEXT)")
        val createTableQuery2=("CREATE TABLE $CARD_TABLE($CARD_ID INTEGER PRIMARY KEY AUTOINCREMENT,$CARD_FRONT TEXT, $CARD_BACK TEXT, $CARD_FK INTEGER, FOREIGN KEY ($CARD_FK) REFERENCES $TABLE_NAME($COLUMN_ID) ON DELETE CASCADE ON UPDATE CASCADE")
        db?.execSQL(createTableQuery)
        db?.execSQL(createTableQuery2)
    }
    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        val dropTableQuery="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }
    fun insertDeck(deckName :String){
        val values= ContentValues().apply{
            put(COLUMN_DECK_NAME, deckName)
        }
        val db= writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
        return
    }
    fun returnDeckAmount():Long{
        val db=readableDatabase
        val rv=DatabaseUtils.queryNumEntries(db, TABLE_NAME)
        db.close()
        return rv
    }
    fun returnDeckName(deckID:Long): String {
        val db=readableDatabase
        val selection = "$COLUMN_ID=?"
        val selectionArgs= arrayOf( deckID.toString())
        var rv =""
        val cursor= db.query(TABLE_NAME,
            arrayOf("ID"), selection, selectionArgs, null, null,null)
        if (cursor.moveToFirst()){
            rv =cursor.getString(0)
        }
        cursor.close()
        db.close()
        return rv
    }
    fun deleteDeck(deckID:Long){
        val db=writableDatabase
        db.delete(TABLE_NAME,"deckID=?", arrayOf(deckID.toString()))
        for (IDnumber in 0..returnDeckAmount()){
            if (IDnumber>deckID){
                val newID=IDnumber-1
                val values = ContentValues()
                values.put("deckID",newID)
                db.update(TABLE_NAME, values,"deckID=?",arrayOf((IDnumber).toString()))
            }
        }
        db.close()
        return
    }
    fun renameDeck(deckID: Long,newName:String){
        val db=writableDatabase
        val values=ContentValues()
        values.put("deckName",newName)
        db.update(TABLE_NAME,values,"deckID=?",arrayOf(deckID.toString()))
        db.close()
        return
    }
    companion object{
        private const val DATABASE_NAME = "FlashCards.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME="Decks"
        private const val COLUMN_ID="deckID"
        private const val COLUMN_DECK_NAME ="deckName"
        private const val CARD_TABLE="Cards"
        private const val CARD_ID="cardID"
        private const val CARD_FRONT="front"
        private const val CARD_BACK="back"
        private const val CARD_FK="deckID"

    }
}