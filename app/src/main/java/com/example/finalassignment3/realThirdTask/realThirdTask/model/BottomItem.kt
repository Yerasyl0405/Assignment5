package  com.example.finalassignment3.realThirdTask.realThirdTask.model
import com.example.finalassignment3.R


open class BottomItem( val iconId: Int, val route: String) {
    object Screen1 : BottomItem( R.drawable.icons, "MovieScreen")
    object Screen2 : BottomItem( R.drawable.icons__1_, "screen_2")
    object Screen3 : BottomItem( R.drawable.icons__2_, "screen_3")
}