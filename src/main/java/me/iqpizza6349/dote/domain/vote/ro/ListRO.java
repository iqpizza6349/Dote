package me.iqpizza6349.dote.domain.vote.ro;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListRO<T> {

    private List<T> contents;

}
