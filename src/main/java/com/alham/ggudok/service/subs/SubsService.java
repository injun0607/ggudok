package com.alham.ggudok.service.subs;

import com.alham.ggudok.repository.subs.SubsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubsService {

    private final SubsRepository subsRepository;


}
