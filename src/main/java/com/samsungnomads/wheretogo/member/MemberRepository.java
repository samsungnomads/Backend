package com.samsungnomads.wheretogo.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public String save(Member member) {
        em.persist(member);
        return member.getId();
    }

}
