package kiz.space.repository;

import kiz.space.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Member.MemberPk> {

}
