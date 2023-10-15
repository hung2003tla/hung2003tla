package com.example.quanlynguoidung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editName, editTaiKhoan, editMatKhau, editDiaChi, editSDT;
    private Button btnAddUser;
    private Button btnEditUser;
    private Button btnDeleteUser;
    private ListView listViewUsers;

    private ArrayAdapter<String> adapter; // Thay đổi ArrayAdapter để chỉ hiển thị tên
    private List<User> userList;
    private List<String> userNameList; // Danh sách tên người dùng
    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các thành phần giao diện
        editName = findViewById(R.id.editName);
        editTaiKhoan = findViewById(R.id.editTaiKhoan);
        editMatKhau = findViewById(R.id.editMatKhau);
        editDiaChi = findViewById(R.id.editDiaChi);
        editSDT = findViewById(R.id.editSDT);
        btnAddUser = findViewById(R.id.btnAddUser);
        listViewUsers = findViewById(R.id.listViewUsers);
        btnDeleteUser=findViewById(R.id.btnDeleteUser);
        btnEditUser=findViewById(R.id.btnEditUser);

        // Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Khởi tạo danh sách người dùng và danh sách tên người dùng
        userList = new ArrayList<>();
        userNameList = new ArrayList<>();

        // Khởi tạo ArrayAdapter và thiết lập cho ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userNameList); // Sử dụng danh sách tên
        listViewUsers.setAdapter(adapter);

        // Thêm sự kiện click cho nút "Thêm người dùng"
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin từ EditText
                String name = editName.getText().toString();
                String taikhoan = editTaiKhoan.getText().toString();
                String matkhau = editMatKhau.getText().toString();
                String diachi = editDiaChi.getText().toString();
                String sdt = editSDT.getText().toString();

                // Tạo đối tượng User
                User user = new User();
                user.setName(name);
                user.setTaikhoan(taikhoan);
                user.setMatkhau(matkhau);
                user.setDiachi(diachi);
                user.setSdt(sdt);

                // Thêm người dùng vào cơ sở dữ liệu
                long userId = dbHelper.addUser(user);

                if (userId != -1) {
                    // Người dùng đã được thêm thành công
                    user.setId((int) userId);
                    userList.add(user);

                    // Thêm tên người dùng vào danh sách tên
                    userNameList.add(user.getName());

                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
                } else {
                    // Thất bại khi thêm người dùng
                    Toast.makeText(MainActivity.this, "Thêm người dùng thất bại", Toast.LENGTH_SHORT).show();
                }

                // Xóa nội dung trong EditText sau khi thêm người dùng thành công hoặc thất bại
                editName.getText().clear();
                editTaiKhoan.getText().clear();
                editMatKhau.getText().clear();
                editDiaChi.getText().clear();
                editSDT.getText().clear();
            }
        });
        // Thêm sự kiện click cho nút "Xóa" trong danh sách người dùng
        listViewUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = userList.get(position);
                deleteUser(selectedUser);
                return true; // Đánh dấu sự kiện đã được xử lý
            }
        });
        // Thêm sự kiện click cho nút "Sửa" trong danh sách người dùng
        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = userList.get(position);
                startEditUserActivity(selectedUser);
            }
        });

        // Hiển thị danh sách người dùng ban đầu
        listViewUsers.setAdapter(adapter);
        userList.addAll(dbHelper.getAllUsers());

        // Thêm tên người dùng vào danh sách tên
        for (User user : userList) {
            userNameList.add(user.getName());
        }

        adapter.notifyDataSetChanged();
    }
    private void deleteUser(User user) {
        long deletedRows = dbHelper.deleteUser(user.getId());
        if (deletedRows > 0) {
            userList.remove(user);

            // Xóa tên người dùng khỏi danh sách tên
            userNameList.remove(user.getName());
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Xóa người dùng thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Xóa người dùng thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void startEditUserActivity(User selectedUser) {
        Intent intent = new Intent(this, EditUserActivity.class);
        intent.putExtra("user_id", selectedUser.getId());
        startActivity(intent);
    }
}
