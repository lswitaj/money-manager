package com.lswitaj.moneymanager.data.database

//TODO(to create a user object)
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//TODO(to consider getting rid of the snake_case and do camelCase without a ColumnInfo annotation)
//TODO(to add a type - stock, crypto, etf, cash)
@Parcelize
@Entity(tableName = "positions")
data class Position(
    @ColumnInfo(name = "positionName")
    var positionName: String,

    //TODO(add a timestamp so that the app knows if the position price should/not be updated)
    //TODO(to change the type to Double and add a text formatter)
    @ColumnInfo(name = "buyPrice") var buyPrice: String = "0.0",
    @ColumnInfo(name = "quantity") var quantity: String = "0.0",
    @ColumnInfo(name = "lastClosePrice") var lastClosePrice: String = "0.0"
) : Parcelable {
    @PrimaryKey(autoGenerate = true) var positionId: Long = 0L
}