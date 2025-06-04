package com.notificationapi.notificationapi.repositories;

import com.notificationapi.notificationapi.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    // Aqui você pode adicionar métodos personalizados depois, se quiser.
}