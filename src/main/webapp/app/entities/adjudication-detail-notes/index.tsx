import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AdjudicationDetailNotes from './adjudication-detail-notes';
import AdjudicationDetailNotesDetail from './adjudication-detail-notes-detail';
import AdjudicationDetailNotesUpdate from './adjudication-detail-notes-update';
import AdjudicationDetailNotesDeleteDialog from './adjudication-detail-notes-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AdjudicationDetailNotesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AdjudicationDetailNotesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AdjudicationDetailNotesDetail} />
      <ErrorBoundaryRoute path={match.url} component={AdjudicationDetailNotes} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AdjudicationDetailNotesDeleteDialog} />
  </>
);

export default Routes;
