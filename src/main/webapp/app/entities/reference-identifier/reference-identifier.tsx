import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './reference-identifier.reducer';
import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IReferenceIdentifierProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ReferenceIdentifier = (props: IReferenceIdentifierProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { referenceIdentifierList, match, loading } = props;
  return (
    <div>
      <h2 id="reference-identifier-heading" data-cy="ReferenceIdentifierHeading">
        <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.home.title">Reference Identifiers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.home.createLabel">Create new Reference Identifier</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {referenceIdentifierList && referenceIdentifierList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.ref">Ref</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.idValue">Id Value</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.identifier">Identifier</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.display">Display</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.item">Item</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.detailItem">Detail Item</Translate>
                </th>
                <th>
                  <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.subDetailItem">Sub Detail Item</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {referenceIdentifierList.map((referenceIdentifier, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${referenceIdentifier.id}`} color="link" size="sm">
                      {referenceIdentifier.id}
                    </Button>
                  </td>
                  <td>{referenceIdentifier.ref}</td>
                  <td>{referenceIdentifier.idValue}</td>
                  <td>{referenceIdentifier.identifier}</td>
                  <td>{referenceIdentifier.display}</td>
                  <td>
                    {referenceIdentifier.item ? <Link to={`item/${referenceIdentifier.item.id}`}>{referenceIdentifier.item.id}</Link> : ''}
                  </td>
                  <td>
                    {referenceIdentifier.detailItem ? (
                      <Link to={`detail-item/${referenceIdentifier.detailItem.id}`}>{referenceIdentifier.detailItem.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {referenceIdentifier.subDetailItem ? (
                      <Link to={`sub-detail-item/${referenceIdentifier.subDetailItem.id}`}>{referenceIdentifier.subDetailItem.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${referenceIdentifier.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${referenceIdentifier.id}/edit`}
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
                        to={`${match.url}/${referenceIdentifier.id}/delete`}
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
              <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.home.notFound">No Reference Identifiers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ referenceIdentifier }: IRootState) => ({
  referenceIdentifierList: referenceIdentifier.entities,
  loading: referenceIdentifier.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ReferenceIdentifier);
