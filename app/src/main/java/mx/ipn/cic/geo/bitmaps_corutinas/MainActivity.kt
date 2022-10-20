package mx.ipn.cic.geo.bitmaps_corutinas

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mx.ipn.cic.geo.bitmaps_corutinas.databinding.ActivityMainBinding
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
   // Atributo para acceder a los widgets mediante vinculación de vista.
   private lateinit var binding: ActivityMainBinding

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      // Se habilita como comentario el setContentView.
      // setContentView(R.layout.activity_main)
      initBindingView()
      initButtonListener()
   }

   private fun initBindingView() {
      binding = ActivityMainBinding.inflate(layoutInflater)
      val view = binding.root
      setContentView(view)
      // Ocultar el ActionBar.
      this.supportActionBar?.hide()

   }

   private fun initButtonListener() {
      // Recuperar la referencia al widget usando vinculación de vistas.
      var imageViewBitmap = binding.imagen


      binding.btnCorutina.setOnClickListener {
         MainScope().launch {
            //Toast.makeText(applicationContext, "Subrutina iniciada...", Toast.LENGTH_SHORT).show()
            //delay(3000)
            var nombre: String = "medieval" + Random.nextInt(0, 9).toString()
            val id  = resources.getIdentifier(nombre, "drawable", packageName)

            imageViewBitmap?.setImageBitmap(
               decodeSampledBitmapFromResource(
                  resources,
                  id,
                  imageViewBitmap!!.width,
                  imageViewBitmap!!.height
               )
            )

            //Toast.makeText(applicationContext, "Subrutina terminada :D...", Toast.LENGTH_SHORT).show()
         }
      }
   }

   private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
      // Raw height and width of image
      val height = options.outHeight
      val width = options.outWidth
      var inSampleSize = 1

      // Tamaño original del mapa de bits (width 3300 x height 1650).
      // Tamaño deseado (reqWidth 330 x reqHeight 200).
      if (height > reqHeight || width > reqWidth) {

         // Calculate ratios of height and width to requested height and width
         // piso (floor) , techo (cell).
         val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt() // Res: 8
         val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt() // Res: 10.

         // Choose the smallest ratio as inSampleSize value, this will guarantee
         // a final image with both dimensions larger than or equal to the
         // requested height and width.
         inSampleSize = max(heightRatio, widthRatio) // 8
      }
      return inSampleSize
   }

   private fun decodeSampledBitmapFromResource(res: Resources?, resId: Int,
                                               reqWidth: Int, reqHeight: Int): Bitmap?
   {
      // First decode with inJustDecodeBounds=true to check dimensions
      val options = BitmapFactory.Options()
      // De forma predeterminada el valor predeterminado es false. (default value is false).
      options.inJustDecodeBounds = true
      BitmapFactory.decodeResource(res, resId, options)

      // Calculate inSampleSize
      options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

      // Decode bitmap with inSampleSize set
      options.inJustDecodeBounds = false
      return BitmapFactory.decodeResource(res, resId, options)
   }





}