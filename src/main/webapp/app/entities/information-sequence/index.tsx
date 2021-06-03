import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InformationSequence from './information-sequence';
import InformationSequenceDetail from './information-sequence-detail';
import InformationSequenceUpdate from './information-sequence-update';
import InformationSequenceDeleteDialog from './information-sequence-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InformationSequenceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InformationSequenceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InformationSequenceDetail} />
      <ErrorBoundaryRoute path={match.url} component={InformationSequence} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InformationSequenceDeleteDialog} />
  </>
);

export default Routes;
