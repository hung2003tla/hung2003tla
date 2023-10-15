package com.example.quanlynguoidung;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
    public class EditUserActivity extends AppCompatActivity {
        private EditText editName, editTaiKhoan, editMatKhau, editDiaChi, editSDT;
        private Button btnSave;
        private Button btnDelete;
        private User currentUser; // Lưu trữ thông tin người dùng hiện tại
        private DatabaseHelper dbHelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_user);

            // Khởi tạo các thành phần giao diện
            editName = findViewById(R.id.editName);
            editTaiKhoan = findViewById(R.id.editTaiKhoan);
            editMatKhau = findViewById(R.id.editMatKhau);
            editDiaChi = findViewById(R.id.editDiaChi);
            editSDT = findViewById(R.id.editSDT);
            btnSave = findViewById(R.id.btnSave);

            // Khởi tạo DatabaseHelper
            dbHelper = new DatabaseHelper(this);

            // Lấy thông tin người dùng hiện tại từ Intent
            long userId = getIntent().getLongExtra("user_id", -1);
            if (userId != -1) {
                currentUser = dbHelper.getUser(userId);
                if (currentUser != null) {
                    // Hiển thị thông tin người dùng hiện tại trong EditText
                    editName.setText(currentUser.getName());
                    editTaiKhoan.setText(currentUser.getTaikhoan());
                    editMatKhau.setText(currentUser.getMatkhau());
                    editDiaChi.setText(currentUser.getDiachi());
                    editSDT.setText(currentUser.getSdt());
                }
            }
            Button btnDelete = findViewById(R.id.btnDelete);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Kiểm tra nếu người dùng hiện tại không null
                        // Xóa người dùng từ cơ sở dữ liệu
                        int deletedRows = (int) dbHelper.deleteUser(currentUser.getId());
                        if (deletedRows > 0) {
                            Toast.makeText(EditUserActivity.this, "Xóa người dùng thành công", Toast.LENGTH_SHORT).show();
                            finish(); // Đóng hoạt động chỉnh sửa và trở về danh sách người dùng
                        }
                    }
            });



            // Thêm sự kiện click cho nút "Lưu"
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser != null) {
                        // Cập nhật thông tin người dùng
                        currentUser.setName(editName.getText().toString());
                        currentUser.setTaikhoan(editTaiKhoan.getText().toString());
                        currentUser.setMatkhau(editMatKhau.getText().toString());
                        currentUser.setDiachi(editDiaChi.getText().toString());
                        currentUser.setSdt(editSDT.getText().toString());

                        // Cập nhật thông tin trong cơ sở dữ liệu
                        int updatedRows = dbHelper.updateUser(currentUser);
                        if (updatedRows > 0) {
                            Toast.makeText(EditUserActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                            finish(); // Đóng hoạt động chỉnh sửa và trở về danh sách người dùng
                        } else {
                            Toast.makeText(EditUserActivity.this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    finish();
                }
            });
        }
    }
