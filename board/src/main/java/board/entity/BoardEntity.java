package board.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // 해당 클래스가 JPA의 엔티티임을 나타냄. 이 클래스는 테이블과 매핑됨.
@Table(name="t_jpa_board") // t_jpa_board 테이블과 매핑되도록 함.
@NoArgsConstructor
@Data
public class BoardEntity {
	@Id // 엔티티의 기본키 PK
	@GeneratedValue(strategy=GenerationType.AUTO) //기본키의 생성을 설정한다. 이렇게 하면 NySQL은 기본키가 자동 증가됨. 오라클의 경우 기본키에 사용할 시퀀스를 생성하게 됨.
	private int boardIdx;

	@Column(nullable=false)
	private String title;
	
	@Column(nullable=false)
	private String contents;
	
	@Column(nullable=false)
	private int hitCnt = 0;
	
	@Column(nullable=false)
	private String creatorId;
	
	@Column(nullable=false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime createdDatetime = LocalDateTime.now(); // 데이터베이스에서 제공하는 초기 시간값대로 사용하는건 좋지않다.
	
	private String updaterId;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime updatedDatetime;

	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL) // 1:N 관계
	@JoinColumn(name="board_idx") // 관계가 있는 테이블의 컬럼을 지정한다.
	private Collection<BoardFileEntity> fileList;
}