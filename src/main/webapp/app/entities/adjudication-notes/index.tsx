import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AdjudicationNotes from './adjudication-notes';
import AdjudicationNotesDetail from './adjudication-notes-detail';
import AdjudicationNotesUpdate from './adjudication-notes-update';
import AdjudicationNotesDeleteDialog from './adjudication-notes-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AdjudicationNotesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AdjudicationNotesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AdjudicationNotesDetail} />
      <ErrorBoundaryRoute path={match.url} component={AdjudicationNotes} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AdjudicationNotesDeleteDialog} />
  </>
);

export default Routes;
