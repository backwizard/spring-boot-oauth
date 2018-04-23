package com.example.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.MemberType;
import com.example.model.User;
import com.example.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User saveUser(User user) {
		// TODO clean it
		try {
			user.setMemberType(mapSalary(user.getSalary()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		String phone = user.getPhone().substring(user.getPhone().length() - 3);
		String dateNow = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
		user.setRefCode(dateNow.concat(phone));
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		return userRepository.save(user);
	}

	private MemberType mapSalary(BigDecimal salary) throws Exception {
		// TODO clean it
		BigDecimal highSalary = new BigDecimal(50000.00);
		BigDecimal midSalary = new BigDecimal(30000.00);
		BigDecimal lowSalary = new BigDecimal(15000.00);

		if (salary.compareTo(highSalary) == 1) {
			return MemberType.Platinum;
		} else if (salary.compareTo(highSalary) == 0
				&& (salary.compareTo(midSalary) == 1 || salary.compareTo(midSalary) == 0)) {
			return MemberType.Gold;
		} else if (salary.compareTo(midSalary) == -1
				&& (salary.compareTo(lowSalary) == 0 || salary.compareTo(lowSalary) == 1)) {
			return MemberType.Silver;
		} else {
			throw new Exception();
		}
	}

}
