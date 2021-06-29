import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './response-insurance-item.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ResponseInsuranceItemDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const responseInsuranceItemEntity = useAppSelector(state => state.responseInsuranceItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="responseInsuranceItemDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.detail.title">ResponseInsuranceItem</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{responseInsuranceItemEntity.id}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.category">Category</Translate>
            </span>
          </dt>
          <dd>{responseInsuranceItemEntity.category}</dd>
          <dt>
            <span id="excluded">
              <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.excluded">Excluded</Translate>
            </span>
          </dt>
          <dd>{responseInsuranceItemEntity.excluded ? 'true' : 'false'}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.name">Name</Translate>
            </span>
          </dt>
          <dd>{responseInsuranceItemEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.description">Description</Translate>
            </span>
          </dt>
          <dd>{responseInsuranceItemEntity.description}</dd>
          <dt>
            <span id="network">
              <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.network">Network</Translate>
            </span>
          </dt>
          <dd>{responseInsuranceItemEntity.network}</dd>
          <dt>
            <span id="unit">
              <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.unit">Unit</Translate>
            </span>
          </dt>
          <dd>{responseInsuranceItemEntity.unit}</dd>
          <dt>
            <span id="term">
              <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.term">Term</Translate>
            </span>
          </dt>
          <dd>{responseInsuranceItemEntity.term}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.responseInsuranceItem.responseInsurance">Response Insurance</Translate>
          </dt>
          <dd>{responseInsuranceItemEntity.responseInsurance ? responseInsuranceItemEntity.responseInsurance.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/response-insurance-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/response-insurance-item/${responseInsuranceItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ResponseInsuranceItemDetail;
