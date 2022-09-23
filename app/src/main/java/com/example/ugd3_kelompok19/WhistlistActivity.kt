package com.example.ugd3_kelompok19

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ugd3_kelompok19.room.Constant
import com.example.ugd3_kelompok19.room.Peminjam
import com.example.ugd3_kelompok19.room.PeminjamDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WhistlistActivity : AppCompatActivity() {
    val db by lazy { PeminjamDB(this) }
    lateinit var peminjamAdapter: Peminjam_adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclerView()
    }
    //berfungsi untuk membuat sebuah note status pada button yang ditekan untuk CRUD yang dilaksanakan
    //ini berhubungan dengan Constant status pada room
    //cara panggil id dengan memanggil fungsi intetnEdit.
    //jika pada fungsi interface adapterListener berubah, maka object akan memerah error karena penambahan fungsi.
    private fun setupRecyclerView() {
        peminjamAdapter = Peminjam_adapter(arrayListOf(), object :
            Peminjam_adapter.OnAdapterListener{
            override fun onClick(peminjam: Peminjam) {
                //Toast.makeText(applicationContext, note.title, Toast.LENGTH_SHORT).show()
                intentEdit(peminjam.id, Constant.TYPE_READ)
            }
            override fun onUpdate(peminjam: Peminjam) {
                intentEdit(peminjam.id, Constant.TYPE_UPDATE)
            }
            override fun onDelete(peminjam: Peminjam) {
                deleteDialog(peminjam)
            }
        })
        list_peminjam.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = peminjamAdapter
        }
    }
    private fun deleteDialog(note: Peminjam){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Confirmation")
            setMessage("Are You Sure to delete this data From${note.title}?")
            setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
            })
            setPositiveButton("Delete", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.peminjamDao().deletePeminjam(note)
                    loadData()
                }
            })
        }
        alertDialog.show()
    }
    override fun onStart() {
        super.onStart()
        loadData()
    }
    //untuk load data yang tersimpan pada database yang sudah create data
    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val peminjam = db.peminjamDao().getPeminjam()
            Log.d("MainActivity","dbResponse: $peminjam")
            withContext(Dispatchers.Main){
                peminjamAdapter.setData( peminjam )
            }
        }
    }
    fun setupListener() {
        button_create.setOnClickListener{
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }
    //pick data dari Id yang sebagai primary key
    fun intentEdit(noteId : Int, intentType: Int){
        startActivity(
            Intent(applicationContext, editActivity::class.java)
                .putExtra("intent_id", noteId)
                .putExtra("intent_type", intentType)
        )
    }
}