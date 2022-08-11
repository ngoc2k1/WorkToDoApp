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

import com.bichngoc.worktodoapplication.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private CongViecAdapter congViecAdapter;
    private ArrayList<CongViec> congViecList;
    private CongViecDAO congViecDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        congViecList = new ArrayList<>();
        congViecDAO = new CongViecDAO(this);

        congViecList = congViecDAO.select();
        congViecAdapter = new CongViecAdapter(congViecList, MainActivity.this, new IOnCongViecListener() {
            @Override
            public void delete(int id) {
                deleteDialog(id);
            }

            @Override
            public void update(CongViec congViec) {
                updateDialog(congViec);
            }
        });
        binding.rvMainJob.setAdapter(congViecAdapter);
        binding.buttonMainAdd.setOnClickListener(view1 -> {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.diaglog_themcv);
            EditText editTen = dialog.findViewById(R.id.edittext_tencv);
            Button btnThem = dialog.findViewById(R.id.button_them);
            Button btnHuy = dialog.findViewById(R.id.button_huy);
            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view1) {
                    dialog.dismiss();
                }
            });
            btnThem.setOnClickListener(view11 -> {
                String tencv = editTen.getText().toString();
                if (tencv.trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Hãy nhập tên công việc", Toast.LENGTH_SHORT).show();
                } else {
                    congViecDAO.insert(new CongViec(tencv));
                    Toast.makeText(getApplicationContext(), "Thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    congViecAdapter.updateData(congViecDAO.select());
                    binding.rvMainJob.smoothScrollToPosition(congViecList.size() - 1);
                }
            });
            dialog.show();
        });
    }

    public void deleteDialog(int id) {//xử lí ở Main, gọi ở Adapter
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn muốn xóa công việc không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                congViecDAO.delete(id);
                Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
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

    public void updateDialog(CongViec congViec) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.diaglog_suacv);
        EditText editTextTen = dialog.findViewById(R.id.edittext_tencv_edit);
        editTextTen.setText(congViec.getTenCV());
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
                String tenMoi = editTextTen.getText().toString().trim();
                congViecDAO.update(new CongViec(congViec.getIdCV(), tenMoi));
                Toast.makeText(MainActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                congViecAdapter.updateData(congViecDAO.select());
            }
        });
        dialog.show();
    }
}
//bottomsheetdialog