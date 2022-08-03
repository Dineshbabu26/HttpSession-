package com.example.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserSession, Integer> {

	@Query("from UserSession where userid=?1")
	UserSession findById(int userid);

	@Query("from UserSession where userid=?1")
	void deleteUser(int userid);

	@Query("from UserSession where userid=?1")
	UserSession checkLogin(int id);
}
