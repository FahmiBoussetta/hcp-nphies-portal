import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Note from './note';
import NoteDetail from './note-detail';
import NoteUpdate from './note-update';
import NoteDeleteDialog from './note-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NoteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NoteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NoteDetail} />
      <ErrorBoundaryRoute path={match.url} component={Note} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={NoteDeleteDialog} />
  </>
);

export default Routes;
