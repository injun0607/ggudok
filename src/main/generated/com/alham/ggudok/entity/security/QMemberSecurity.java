package com.alham.ggudok.entity.security;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberSecurity is a Querydsl query type for MemberSecurity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberSecurity extends EntityPathBase<MemberSecurity> {

    private static final long serialVersionUID = -1848788050L;

    public static final QMemberSecurity memberSecurity = new QMemberSecurity("memberSecurity");

    public final StringPath loginId = createString("loginId");

    public final StringPath refreshToken = createString("refreshToken");

    public final NumberPath<Long> securityId = createNumber("securityId", Long.class);

    public QMemberSecurity(String variable) {
        super(MemberSecurity.class, forVariable(variable));
    }

    public QMemberSecurity(Path<? extends MemberSecurity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberSecurity(PathMetadata metadata) {
        super(MemberSecurity.class, metadata);
    }

}

