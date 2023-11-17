package com.alham.ggudok.entity.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1396798260L;

    public static final QMember member = new QMember("member1");

    public final BooleanPath admin = createBoolean("admin");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final StringPath loginId = createString("loginId");

    public final ListPath<MemberFavorSubs, QMemberFavorSubs> memberFavorSubsList = this.<MemberFavorSubs, QMemberFavorSubs>createList("memberFavorSubsList", MemberFavorSubs.class, QMemberFavorSubs.class, PathInits.DIRECT2);

    public final ListPath<MemberHaveSubs, QMemberHaveSubs> memberHaveSubsList = this.<MemberHaveSubs, QMemberHaveSubs>createList("memberHaveSubsList", MemberHaveSubs.class, QMemberHaveSubs.class, PathInits.DIRECT2);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final StringPath memberName = createString("memberName");

    public final ListPath<MemberRelTag, QMemberRelTag> memberRelTags = this.<MemberRelTag, QMemberRelTag>createList("memberRelTags", MemberRelTag.class, QMemberRelTag.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath profileImage = createString("profileImage");

    public final ListPath<Review, QReview> reviews = this.<Review, QReview>createList("reviews", Review.class, QReview.class, PathInits.DIRECT2);

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

