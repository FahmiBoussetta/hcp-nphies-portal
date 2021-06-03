import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AdjudicationSubDetailNotes from './adjudication-sub-detail-notes';
import AdjudicationSubDetailNotesDetail from './adjudication-sub-detail-notes-detail';
import AdjudicationSubDetailNotesUpdate from './adjudication-sub-detail-notes-update';
import AdjudicationSubDetailNotesDeleteDialog from './adjudication-sub-detail-notes-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AdjudicationSubDetailNotesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AdjudicationSubDetailNotesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AdjudicationSubDetailNotesDetail} />
      <ErrorBoundaryRoute path={match.url} component={AdjudicationSubDetailNotes} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AdjudicationSubDetailNotesDeleteDialog} />
  </>
);

export default Routes;
