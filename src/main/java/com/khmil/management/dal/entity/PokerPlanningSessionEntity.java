package com.khmil.management.dal.entity;


import com.khmil.management.enums.DeckType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "poker_planning_sessions")
public class PokerPlanningSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_id", nullable = false, unique = true)
    private String sessionId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "deck_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeckType deckType;

    @Column(name = "invite_link", nullable = false)
    private String inviteLink;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<MemberEntity> members = new ArrayList<>();

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<UserStoryEntity> userStories = new ArrayList<>();

}