package org.springframework.boot.autoconfigure.data.tarantool.user;

import org.springframework.data.tarantool.repository.ReactiveTarantoolRepository;

import java.util.UUID;

public interface ReactiveUserRepository extends ReactiveTarantoolRepository<User, UUID> {
}
