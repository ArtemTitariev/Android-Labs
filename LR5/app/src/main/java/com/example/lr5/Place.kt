import android.os.Parcel
import android.os.Parcelable

data class Place (
    val name: String,
    val description: String,
    val address: String,
    val costOfVisit: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble()
    )

    init {
        // IllegalArgumentException
        require(name.isNotEmpty()) { "Name cannot be empty" }
        require(description.isNotEmpty()) { "Description cannot be empty" }
        require(address.isNotEmpty()) { "Address cannot be empty" }
        require(costOfVisit >= 0) { "Cost of visit cannot be negative" }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(address)
        parcel.writeDouble(costOfVisit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Place> {
        override fun createFromParcel(parcel: Parcel): Place {
            return Place(parcel)
        }

        override fun newArray(size: Int): Array<Place?> {
            return arrayOfNulls(size)
        }
    }
}
