package com.lswitaj.moneymanager.data.database

//TODO(to create a user object)
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//TODO(to add a type - stock, crypto, etf, cash)
//TODO(add the transaction timestamp and updateTime instead of the positionId that can differ between the app and backend)
@Parcelize
@Entity(tableName = "positions")
data class Position(
    @ColumnInfo(name = "positionName")
    var positionName: String,

    //TODO(add a timestamp so that the app knows if the position price should/not be updated)
    //TODO(to change the type to Double and add a text formatter)
    var buyPrice: String = "0.0",
    var quantity: String = "0.0",
    var lastClosePrice: String = "0.0"
) : Parcelable {
    @PrimaryKey(autoGenerate = true) var positionId: Long = 0L

    constructor(position: Position) : this(
        position.positionName,
        position.buyPrice,
        position.quantity,
        position.lastClosePrice
    )

    //TODO(extract error messages to strings.xml)
    fun isReady() : String {
        return when {
            positionName.isNullOrEmpty() -> "Position name can't be empty"
            quantity.toDouble() <= 0.0 -> "Quantity has to be more than 0"
            else -> ""
        }
    }
}