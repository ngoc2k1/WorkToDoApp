package com.bichngoc.worktodoapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private CongViecAdapter congViecAdapter;
    private ArrayList<CongViec> listCongViec;
    private CongViecDAO congViecDAO;
    private RecyclerView rvCongViec;
    private FloatingActionButton buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAdd = findViewById(R.id.buttonAdd);
        rvCongViec = findViewById(R.id.rvCongViec);
        listCongViec = new ArrayList<>();
        congViecDAO = new CongViecDAO(this);

        listCongViec = congViecDAO.select();
        congViecAdapter = new CongViecAdapter(listCongViec, MainActivity.this);
        rvCongViec.setAdapter(congViecAdapter);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.diaglog_themcv);
                EditText editTen = dialog.findViewById(R.id.edittext_tencv);
                Button btnThem = dialog.findViewById(R.id.button_them);
                Button btnHuy = dialog.findViewById(R.id.button_huy);
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tencv = editTen.getText().toString();
                        if (tencv.trim().equals("")) {
                            Toast.makeText(getApplicationContext(), "Please enter", Toast.LENGTH_SHORT).show();
                        } else {
                            congViecDAO.insert(new CongViec(tencv));
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            congViecAdapter.updateData(congViecDAO.select());
                            rvCongViec.smoothScrollToPosition(listCongViec.size() - 1);
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    public void delete(int id, String tencv) {//xử lí ở Main, gọi ở Adapter
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn muốn xóa công việc " + tencv + " không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                congViecDAO.delete(id);
                Toast.makeText(MainActivity.this, "Delete " + tencv, Toast.LENGTH_SHORT).show();
                congViecAdapter.updateData(congViecDAO.select());
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialogXoa.show();
    }

    public void update(CongViec congViec) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.diaglog_suacv);
        EditText edtTen = dialog.findViewById(R.id.edittext_tencv_edit);
        edtTen.setText(congViec.getTenCV());
        Button btnHuy = dialog.findViewById(R.id.button_huy_edit);
        Button btnSave = dialog.findViewById(R.id.button_save);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi = edtTen.getText().toString().trim();
                congViecDAO.update(new CongViec(congViec.getIdCV(), tenMoi));
                Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                congViecAdapter.updateData(congViecDAO.select());
            }
        });
        dialog.show();
    }
}
//bottomsheetdialog