package com.kmmoon.assignment.dto;import lombok.AllArgsConstructor;import lombok.Getter;import lombok.NoArgsConstructor;import java.util.List;@AllArgsConstructor@NoArgsConstructor@Getterpublic class CursorResultDTO<T> {    private List<T> values;    private Boolean hasNext;}