import { ToDoListPage } from './app.po';

describe('to-do-list App', () => {
  let page: ToDoListPage;

  beforeEach(() => {
    page = new ToDoListPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
