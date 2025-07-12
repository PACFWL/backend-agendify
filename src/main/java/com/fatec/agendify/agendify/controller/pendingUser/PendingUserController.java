package com.fatec.agendify.agendify.controller.pendingUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.agendify.agendify.dto.pendingUser.PendingUserCreateDTO;
import com.fatec.agendify.agendify.dto.pendingUser.PendingUserUpdateDTO;
import com.fatec.agendify.agendify.dto.user.UserDTO;
import com.fatec.agendify.agendify.model.pendingUser.PendingUser;
import com.fatec.agendify.agendify.service.pendingUser.PendingUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pendingUser")
public class PendingUserController {
    
    @Autowired
    private PendingUserService pendingUserService;

   @GetMapping("/pending/{id}")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<PendingUser> getPendingUserById(@PathVariable String id) {
        return ResponseEntity.ok(pendingUserService.getPendingUserById(id));
    }

    @PostMapping("/registerPending")
    public ResponseEntity<Map<String, String>> registerPendingUser(@Valid @RequestBody PendingUserCreateDTO pendingUserDTO) {
        PendingUser pending = pendingUserService.createPendingUser(pendingUserDTO);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Registro pendente enviado com sucesso. Aguardando aprovação.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<List<PendingUser>> listPendingUsers() {
        return ResponseEntity.ok(pendingUserService.listPendingUsers());
    }

    @PutMapping("/pending/{id}")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<PendingUser> updatePendingUser(@PathVariable String id, @Valid @RequestBody PendingUserUpdateDTO pendingUserUpdateDTO) {
        return ResponseEntity.ok(pendingUserService.updatePendingUser(id, pendingUserUpdateDTO));
    }
    
    @PostMapping("/pending/{id}/approve")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<UserDTO> approvePendingUser(@PathVariable String id) {
        return ResponseEntity.ok(pendingUserService.approvePendingUser(id));
    }

    @DeleteMapping("/pending/{id}/reject")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<Void> rejectPendingUser(@PathVariable String id) {
        pendingUserService.rejectPendingUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/pending/{id}")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<Void> deletePendingUser(@PathVariable String id) {
        pendingUserService.deletePendingUser(id);
        return ResponseEntity.noContent().build();
    }
}
