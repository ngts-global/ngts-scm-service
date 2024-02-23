package com.ngts.chat.repository;

import com.ngts.chat.entity.Channels;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChannelsRepository extends JpaRepository<Channels, Integer> {
}
