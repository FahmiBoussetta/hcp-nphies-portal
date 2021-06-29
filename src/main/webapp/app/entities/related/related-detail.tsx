import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './related.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RelatedDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const relatedEntity = useAppSelector(state => state.related.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="relatedDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.related.detail.title">Related</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{relatedEntity.id}</dd>
          <dt>
            <span id="relationShip">
              <Translate contentKey="hcpNphiesPortalApp.related.relationShip">Relation Ship</Translate>
            </span>
          </dt>
          <dd>{relatedEntity.relationShip}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.related.claimReference">Claim Reference</Translate>
          </dt>
          <dd>{relatedEntity.claimReference ? relatedEntity.claimReference.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.related.claim">Claim</Translate>
          </dt>
          <dd>{relatedEntity.claim ? relatedEntity.claim.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/related" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/related/${relatedEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RelatedDetail;
