package com.example.food.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.food.activities.MainActivity;
import com.example.food.activities.MainMenuActivity;
import com.example.food.models.Note;
import com.example.food.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DBHandler {

    /*
        //Bu sınıfın yazılma amacı,Database bağlantılarının tek bir sınıf üzerinden yapılmasıdır.
        //Bu sınıfın metodları ile DB üzerinde işlemler yapılmaktadır.
        //Lütfen DB işlemlerini bu sınıfı kullanarak yapınız.
     */

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore firestore;
    private FirebaseStorage firebaseStorage;
    private Intent intent;

    public DBHandler(FirebaseAuth mAuth, FirebaseUser mUser, FirebaseFirestore firestore) {
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        this.mAuth = mAuth;
        this.mUser = mUser;
        this.firestore = firestore;
    }

    public DBHandler(FirebaseAuth mAuth) {
        mAuth = FirebaseAuth.getInstance();
        this.mAuth = mAuth;
    }

    public DBHandler(FirebaseFirestore firestore) {
        firestore = FirebaseFirestore.getInstance();
        this.firestore = firestore;
    }

    public DBHandler(FirebaseAuth mAuth, FirebaseFirestore firestore) {
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        this.mAuth = mAuth;
        this.firestore = firestore;
    }

    public DBHandler(FirebaseAuth mAuth, FirebaseFirestore firestore, FirebaseStorage firebaseStorage) {
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        this.mAuth = mAuth;
        this.firestore = firestore;
        this.firebaseStorage = firebaseStorage;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public FirebaseUser getmUser() {
        return mUser;
    }

    public void setmUser(FirebaseUser mUser) {
        this.mUser = mUser;
    }

    public FirebaseFirestore getFirestore() {
        return firestore;
    }

    public void setFirestore(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    public void addRegister(final User user, final Activity my_registerActivity) {

        getmAuth().createUserWithEmailAndPassword(user.getEmail(), user.getPass())
                .addOnCompleteListener(my_registerActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            setmUser(getmAuth().getCurrentUser());

                            getFirestore().collection("Kullanıcılar").document(getmUser().getUid())
                                    .set(user).addOnCompleteListener(my_registerActivity, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(my_registerActivity, "Kayıt Başarılı.", Toast.LENGTH_SHORT).show();

                                        intent = new Intent(my_registerActivity, MainActivity.class);
                                        my_registerActivity.startActivity(intent);
                                    } else
                                        Toast.makeText(my_registerActivity, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else
                            Toast.makeText(my_registerActivity, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    public void addLogin(String email, final String pass, final Activity loginActivity) {

        getmAuth().signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener(loginActivity, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        /*
                        E-mail password update işlemi Authentication sekmesinde yapıldığı için firestore üzerinde güncel görünmüyordu.
                        UpdatePass methodu ile bu hata giderildi.
                         */
                        updatePass(pass, loginActivity, mAuth.getCurrentUser().getUid());

                        Intent intent = new Intent(loginActivity, MainMenuActivity.class);
                        intent.putExtra("uId", mAuth.getCurrentUser().getUid());
                        loginActivity.startActivity(intent);

                        Toast.makeText(loginActivity, "Giriş Başarılı!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(loginActivity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(loginActivity, "E-Posta veya Şifre Yanlış!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void resetPass(String email, final Activity activity) {
        getmAuth().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                            alert.setTitle("Şifre Sıfırlama İsteği");
                            alert.setMessage("Şifre Sıfırlama Bağlantısı Mail Hesabınıza Gönderildi.");
                            alert.setCancelable(false);
                            alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    intent = new Intent(activity, MainActivity.class);
                                    activity.startActivity(intent);
                                }
                            });
                            alert.show();
                        } else
                            Toast.makeText(activity, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void updatePass(String pass, Activity activity, String uId) {
        firestore.collection("Kullanıcılar")
                .document(uId).update("pass", pass);
    }

    public void DeleteUserFirestore(String uId) {
        firestore.collection("Kullanıcılar")
                .document(uId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Log.d("Başarılı", "Firestore'dan silindi");
                else
                    Log.e("Başarısız", "Firestore'dan silinmedi");
            }
        });
    }

    public void DeleteUserAuthentication(String uId) {
        mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Log.d("Başarılı", "Kişilerden silindi");
                else
                    Log.e("Başarısız", "Kişilerden silinmedi");
            }
        });
    }

    public void getData(final Activity activity, String uId) {
        DocumentReference documentReference = firestore.collection("Kullanıcılar").document(uId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(activity, task.getResult().getData().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void SignOut(Activity activity) {

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Çıkış Yap");
        alert.setMessage("Çıkış Yapmak İstediğinize Emin misiniz?");
        alert.setCancelable(false);
        alert.setPositiveButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setNegativeButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAuth.signOut();
                intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
            }
        });

        alert.show();
    }

    public void DeleteUserPhoto(String uId, Uri uri) {

        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(uri.toString());
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Başarılı", "Silme Başarılı!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Başarısız", e.getMessage());
            }
        });
    }

    public void addNote(Note note, String uId, String title) {
        firestore.collection("Tarifler").document(uId+title).set(note)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Başarılı", "Kayıt Ok.");

                        }
                        else
                            Log.e("Başarısız" , task.getException().toString());
                    }
                });
    }

    public void deleteNotes(String uId){
        firestore.collection("Tarifler")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId().contains(uId)){
                                    firestore.collection("Tarifler").document(document.getId()).delete();
                                    Log.d("Başarılı", "Silindi.");
                                }
                            }

                        }
                        else {
                            Log.e("Başarısız", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
