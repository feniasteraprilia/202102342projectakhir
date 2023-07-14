package com.feniasteraprilia.projectakhir202102342;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FilmActivity extends AppCompatActivity {
    EditText judulfilm, genre, bahasa, tahun;
    Button simpan, tampil, hapus, edit;
    DBHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        judulfilm = findViewById(R.id.judulfilm);
        genre = findViewById(R.id.genre);
        bahasa = findViewById(R.id.bahasa);
        tahun = findViewById(R.id.tahun);
        simpan = findViewById(R.id.simpan);
        tampil = findViewById(R.id.tampil);
        hapus = findViewById(R.id.hapus);
        edit = findViewById(R.id.edit);
        db = new DBHelper(this);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jf = judulfilm.getText().toString();
                String gen = genre.getText().toString();
                String bah = bahasa.getText().toString();
                String thn = tahun.getText().toString();
                if (TextUtils.isEmpty(jf) || TextUtils.isEmpty(gen) || TextUtils.isEmpty(bah) || TextUtils.isEmpty(thn))
                    Toast.makeText(FilmActivity.this, "Semua Field Wajib diIsi", Toast.LENGTH_SHORT).show();
                else {
                    if (!jf.equals("")) {
                        Boolean checkkode = db.checkfilm(jf);
                        if (checkkode == false) {
                            Boolean insert = db.insertDatafilm(jf, gen, bah, thn);
                            if (insert == true) {
                                Toast.makeText(FilmActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(FilmActivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(FilmActivity.this, "Data Film Sudah Ada", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(FilmActivity.this, "", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.tampilDatafilm();
                if (res.getCount()==0){
                    Toast.makeText(FilmActivity.this, "Tidak Ada Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Judul Film : "+ res.getString(0) + "\n");
                    buffer.append("Genre : "+ res.getString(1) + "\n");
                    buffer.append("Bahasa : "+ res.getString(2) + "\n");
                    buffer.append("Tahun : "+ res.getString(3) + "\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(FilmActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Data Film");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jf = judulfilm.getText().toString();
                Boolean cekHapusData = db.hapusDatafilm(jf);
                if (cekHapusData == true)
                    Toast.makeText(FilmActivity.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(FilmActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jf = judulfilm.getText().toString();
                String gen = genre.getText().toString();
                String bah = bahasa.getText().toString();
                String thn = tahun.getText().toString();
                if (TextUtils.isEmpty(jf) || TextUtils.isEmpty(gen) || TextUtils.isEmpty(bah)
                        || TextUtils.isEmpty(thn)) {
                    Toast.makeText(FilmActivity.this, "Semua Field Wajib Diisi", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean edit = db.editDatafilm(jf, gen, bah, thn);
                    if (edit == true) {
                        Toast.makeText(FilmActivity.this, "Data Berhasil Diedit", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FilmActivity.this, "Data Gagal Diedit", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
