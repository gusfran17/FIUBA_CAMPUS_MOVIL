package ar.uba.fi.fiubappREST.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ar.uba.fi.fiubappREST.domain.Discussion;
import ar.uba.fi.fiubappREST.domain.DiscussionMessage;
import ar.uba.fi.fiubappREST.domain.DiscussionReportInformation;
import ar.uba.fi.fiubappREST.domain.Group;
import ar.uba.fi.fiubappREST.domain.Student;
import ar.uba.fi.fiubappREST.persistance.DiscussionRepository;
import ar.uba.fi.fiubappREST.persistance.GroupRepository;

public class ReportServiceImplTest {
	
	private static final int GROUP_MEMBERS = 15;

	private static final String A_GROUP_NAME = "aGroupName";

	private final static Integer VALUES = 2;
	
	private final static Integer A_DISCUSSION_ID = 1;
	
	private final static Integer ANOTHER_DISCUSSION_ID = 2;
	
	private final static Integer YET_ANOTHER_DISCUSSION_ID = 3;
	
	private final static String A_DISCUSSION_NAME = "aDiscussionName";
	
	private final static String ANOTHER_DISCUSSION_NAME = "anotherDiscussionName";
	
	private final static String YET_ANOTHER_DISCUSSION_NAME = "yetAnotherDiscussionName";

	private static final Integer VALUES_GREATER_THAN_SIZE = 5;
	
	@Mock
	private GroupRepository groupRepository;
	@Mock
	private DiscussionRepository discussionRepository;
	@Mock
	private Date dateFrom;
	@Mock
	private Date dateTo;
	@Mock
	private Date dateInRange;
	@Mock
	private Date dateNotInRange;
	@Mock
	private Date anotherDateNotInRange;
	@Mock
	private Group group;
		
	private ReportService service;
	
	@Before
	public void setUp() throws ParseException{
		this.groupRepository = mock(GroupRepository.class);
		this.discussionRepository = mock(DiscussionRepository.class);		
		this.service = new ReportServiceImpl(groupRepository, discussionRepository, null, null);
	}
		
	@Test
	public void testGetMostActiveDiscussions() {
		this.mockDiscussions();
		
		List<DiscussionReportInformation> information = this.service.getMostActiveDiscussions(dateFrom, dateTo, VALUES);
		
		assertEquals(2, information.size());
		assertEquals(A_GROUP_NAME, information.get(0).getGroupName());
		assertEquals(A_DISCUSSION_NAME, information.get(0).getDiscussionName());
		assertEquals(GROUP_MEMBERS, information.get(0).getAmountOfGroupMembers().intValue());
		assertEquals(3, information.get(0).getAmountOfComments().intValue());
		assertEquals(A_GROUP_NAME, information.get(1).getGroupName());
		assertEquals(ANOTHER_DISCUSSION_NAME, information.get(1).getDiscussionName());
		assertEquals(GROUP_MEMBERS, information.get(1).getAmountOfGroupMembers().intValue());
		assertEquals(2, information.get(1).getAmountOfComments().intValue());
	}
	
	@Test
	public void testGetMostActiveDiscussionsGreaterRange() {
		this.mockDiscussions();
		
		List<DiscussionReportInformation> information = this.service.getMostActiveDiscussions(dateFrom, dateTo, VALUES_GREATER_THAN_SIZE);
		
		assertEquals(3, information.size());
		assertEquals(A_GROUP_NAME, information.get(0).getGroupName());
		assertEquals(A_DISCUSSION_NAME, information.get(0).getDiscussionName());
		assertEquals(GROUP_MEMBERS, information.get(0).getAmountOfGroupMembers().intValue());
		assertEquals(3, information.get(0).getAmountOfComments().intValue());
		assertEquals(A_GROUP_NAME, information.get(1).getGroupName());
		assertEquals(ANOTHER_DISCUSSION_NAME, information.get(1).getDiscussionName());
		assertEquals(GROUP_MEMBERS, information.get(1).getAmountOfGroupMembers().intValue());
		assertEquals(2, information.get(1).getAmountOfComments().intValue());
		assertEquals(A_GROUP_NAME, information.get(2).getGroupName());
		assertEquals(YET_ANOTHER_DISCUSSION_NAME, information.get(2).getDiscussionName());
		assertEquals(GROUP_MEMBERS, information.get(2).getAmountOfGroupMembers().intValue());
		assertEquals(1, information.get(2).getAmountOfComments().intValue());
	}

	private void mockDiscussions() {
		dateFrom = mock(Date.class);
		dateTo = mock(Date.class);
		
		dateInRange = mock(Date.class);
		when(dateInRange.after(dateFrom)).thenReturn(true);
		when(dateInRange.before(dateTo)).thenReturn(true);
		
		dateNotInRange = mock(Date.class);
		when(dateNotInRange.after(dateFrom)).thenReturn(false);
		when(dateNotInRange.before(dateTo)).thenReturn(false);
		
		anotherDateNotInRange = mock(Date.class);
		when(anotherDateNotInRange.after(dateFrom)).thenReturn(true);
		when(anotherDateNotInRange.before(dateTo)).thenReturn(false);
		
		group = mock(Group.class);
		when(group.getName()).thenReturn(A_GROUP_NAME);
		@SuppressWarnings("unchecked")
		Set<Student> members = mock(Set.class);
		when(members.size()).thenReturn(GROUP_MEMBERS);
		when(group.getMembers()).thenReturn(members);
		
		when(this.groupRepository.findGroupForDiscussion(A_DISCUSSION_ID)).thenReturn(group);
		when(this.groupRepository.findGroupForDiscussion(ANOTHER_DISCUSSION_ID)).thenReturn(group);
		when(this.groupRepository.findGroupForDiscussion(YET_ANOTHER_DISCUSSION_ID)).thenReturn(group);
		
		Discussion aDiscussion = mock(Discussion.class);
		when(aDiscussion.getId()).thenReturn(A_DISCUSSION_ID);
		when(aDiscussion.getDiscussionName()).thenReturn(A_DISCUSSION_NAME);
		Discussion anotherDiscussion = mock(Discussion.class);
		when(anotherDiscussion.getId()).thenReturn(ANOTHER_DISCUSSION_ID);
		when(anotherDiscussion.getDiscussionName()).thenReturn(ANOTHER_DISCUSSION_NAME);
		Discussion yetAnotherDiscussion = mock(Discussion.class);
		when(yetAnotherDiscussion.getId()).thenReturn(YET_ANOTHER_DISCUSSION_ID);
		when(yetAnotherDiscussion.getDiscussionName()).thenReturn(YET_ANOTHER_DISCUSSION_NAME);
		List<Discussion> discussions = new ArrayList<Discussion>();
		discussions.add(aDiscussion);
		discussions.add(anotherDiscussion);
		discussions.add(yetAnotherDiscussion);
		
		DiscussionMessage aDiscussionFirstMessage = mock(DiscussionMessage.class);
		when(aDiscussionFirstMessage.getCreationDate()).thenReturn(dateInRange);
		DiscussionMessage aDiscussionSecondMessage = mock(DiscussionMessage.class);
		when(aDiscussionSecondMessage.getCreationDate()).thenReturn(dateInRange);
		DiscussionMessage aDiscussionThirdMessage = mock(DiscussionMessage.class);
		when(aDiscussionThirdMessage.getCreationDate()).thenReturn(dateInRange);
		Set<DiscussionMessage> aDiscussionMessages = new HashSet<DiscussionMessage>();
		aDiscussionMessages.add(aDiscussionFirstMessage);
		aDiscussionMessages.add(aDiscussionSecondMessage);
		aDiscussionMessages.add(aDiscussionThirdMessage);
		when(aDiscussion.getMessages()).thenReturn(aDiscussionMessages);
		
		DiscussionMessage anotherDiscussionFirstMessage = mock(DiscussionMessage.class);
		when(anotherDiscussionFirstMessage.getCreationDate()).thenReturn(dateInRange);
		DiscussionMessage anotherDiscussionSecondMessage = mock(DiscussionMessage.class);
		when(anotherDiscussionSecondMessage.getCreationDate()).thenReturn(dateInRange);
		DiscussionMessage anotherDiscussionThirdMessage = mock(DiscussionMessage.class);
		when(anotherDiscussionThirdMessage.getCreationDate()).thenReturn(dateNotInRange);
		Set<DiscussionMessage> anotherDiscussionMessages = new HashSet<DiscussionMessage>();
		anotherDiscussionMessages.add(anotherDiscussionFirstMessage);
		anotherDiscussionMessages.add(anotherDiscussionSecondMessage);
		anotherDiscussionMessages.add(anotherDiscussionThirdMessage);
		when(anotherDiscussion.getMessages()).thenReturn(anotherDiscussionMessages);
		
		DiscussionMessage yetAnotherDiscussionFirstMessage = mock(DiscussionMessage.class);
		when(yetAnotherDiscussionFirstMessage.getCreationDate()).thenReturn(dateInRange);
		DiscussionMessage yetAnotherDiscussionSecondMessage = mock(DiscussionMessage.class);
		when(yetAnotherDiscussionSecondMessage.getCreationDate()).thenReturn(dateNotInRange);
		DiscussionMessage yetAnotherDiscussionThirdMessage = mock(DiscussionMessage.class);
		when(yetAnotherDiscussionThirdMessage.getCreationDate()).thenReturn(anotherDateNotInRange);
		Set<DiscussionMessage> yetAnotherDiscussionMessages = new HashSet<DiscussionMessage>();
		yetAnotherDiscussionMessages.add(yetAnotherDiscussionFirstMessage);
		yetAnotherDiscussionMessages.add(yetAnotherDiscussionSecondMessage);
		yetAnotherDiscussionMessages.add(yetAnotherDiscussionThirdMessage);
		when(yetAnotherDiscussion.getMessages()).thenReturn(yetAnotherDiscussionMessages);
		
		when(this.discussionRepository.findAll()).thenReturn(discussions);
	}
	
	
}
