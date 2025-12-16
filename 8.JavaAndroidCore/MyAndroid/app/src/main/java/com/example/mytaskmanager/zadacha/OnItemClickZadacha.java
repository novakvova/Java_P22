package com.example.mytaskmanager.zadacha;

import com.example.mytaskmanager.dto.zadachi.ZadachaItemDTO;

public interface OnItemClickZadacha {
    //При натиску отримуємо задачу
    void onItemClick(ZadachaItemDTO zadacha);
}
