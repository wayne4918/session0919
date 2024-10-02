package com.likelion.lionlib.service;

import com.likelion.lionlib.domain.Member;
import com.likelion.lionlib.domain.Profile;
import com.likelion.lionlib.domain.Role;
import com.likelion.lionlib.dto.CustomUserDetails;
import com.likelion.lionlib.dto.response.MemberResponse;
import com.likelion.lionlib.dto.request.ProfileRequest;
import com.likelion.lionlib.exception.MemberNotFoundException;
import com.likelion.lionlib.exception.ProfileNotFoundException;
import com.likelion.lionlib.repository.MemberRepository;
import com.likelion.lionlib.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    private final GlobalService globalService;

    // Member 생성
    public void createMember(String email, String encodedPassword) {
        Member newMember = Member.builder()
                .email(email)
                .password(encodedPassword)
                .role(Role.BABYLION) // 역할 설정
                .build();
        memberRepository.save(newMember);

        Profile profile = Profile.builder()
                .member(newMember)
                .build();
        profileRepository.save(profile);
    }

    // 로그인 처리
    public boolean login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));

        return member.getPassword().equals(password);
    }

    // 회원 정보 조회
    public MemberResponse findMember(CustomUserDetails customUserDetails) {
        Member member = globalService.findMemberById(customUserDetails.getId());
        return MemberResponse.fromEntity(member);
    }

    // 프로필 수정
    public MemberResponse updateMember(Long memberId, ProfileRequest profileRequest) {
        Member member = globalService.findMemberById(memberId);

        Profile profile = profileRepository.findByMember(member)
                .orElseThrow(() -> new ProfileNotFoundException(member));

        member.updateName(profileRequest.getName());

        profile.updateProfile(
                profileRequest.getBio(),
                profileRequest.getGeneration(),
                profileRequest.getMajor(),
                profileRequest.getImageUrl(),
                profileRequest.getGithubLink()
        );

        memberRepository.save(member);
        profileRepository.save(profile);

        return MemberResponse.fromEntity(member);
    }
}