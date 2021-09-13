package com.cafein.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserDB is a Querydsl query type for UserDB
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserDB extends EntityPathBase<UserDB> {

    private static final long serialVersionUID = -85061899L;

    public static final QUserDB userDB = new QUserDB("userDB");

    public final DateTimePath<java.util.Date> created_at = createDateTime("created_at", java.util.Date.class);

    public final StringPath email = createString("email");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath image = createString("image");

    public final StringPath info = createString("info");

    public final StringPath nickname = createString("nickname");

    public final EnumPath<com.cafein.dto.user.signin.SocialLoginType> oauth = createEnum("oauth", com.cafein.dto.user.signin.SocialLoginType.class);

    public final StringPath oauthId = createString("oauthId");

    public final StringPath password = createString("password");

    public final StringPath status = createString("status");

    public final DateTimePath<java.util.Date> updated_at = createDateTime("updated_at", java.util.Date.class);

    public QUserDB(String variable) {
        super(UserDB.class, forVariable(variable));
    }

    public QUserDB(Path<? extends UserDB> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserDB(PathMetadata metadata) {
        super(UserDB.class, metadata);
    }

}

