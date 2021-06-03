import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './cov-eli-error-messages.reducer';
import { ICovEliErrorMessages } from 'app/shared/model/cov-eli-error-messages.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICovEliErrorMessagesProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const CovEliErrorMessages = (props: ICovEliErrorMessagesProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { covEliErrorMessagesList, match, loading } = props;
  return (
    <div>
      <h2 id="cov-eli-error-messages-heading" data-cy="CovEliErrorMessagesHeading">
        <Translate contentKey="hcpNphiesPortalApp.covEliErrorMessages.home.title">Cov Eli Error Messages</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.covEliErrorMessages.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.covEliErrorMessages.home.createLabel">Create new Cov Eli Error Messages</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {covEliErrorMessagesList && covEliErrorMessagesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.covEliErrorMessages.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.covEliErrorMessages.message">Message</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.covEliErrorMessages.coverageEligibilityRequest">
                    Coverage Eligibility Request
                  </Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {covEliErrorMessagesList.map((covEliErrorMessages, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${covEliErrorMessages.id}`} color="link" size="sm">
                      {covEliErrorMessages.id}
                    </Button>
                  </td>
                  <td>{covEliErrorMessages.message}</td>
                  <td>
                    {covEliErrorMessages.coverageEligibilityRequest ? (
                      <Link to={`coverage-eligibility-request/${covEliErrorMessages.coverageEligibilityRequest.id}`}>
                        {covEliErrorMessages.coverageEligibilityRequest.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${covEliErrorMessages.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${covEliErrorMessages.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${covEliErrorMessages.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="hcpNphiesPortalApp.covEliErrorMessages.home.notFound">No Cov Eli Error Messages found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ covEliErrorMessages }: IRootState) => ({
  covEliErrorMessagesList: covEliErrorMessages.entities,
  loading: covEliErrorMessages.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CovEliErrorMessages);
