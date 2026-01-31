package com.hungerbox.menu_service.repository;

import com.hungerbox.menu_service.entity.MenuItemAddon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemAddonRepository extends JpaRepository<MenuItemAddon, Long> {
}
